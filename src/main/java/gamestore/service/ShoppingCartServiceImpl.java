package gamestore.service;

import gamestore.model.entity.Product;
import gamestore.model.entity.ShoppingCart;
import gamestore.model.entity.ShoppingCartProduct;
import gamestore.model.entity.User;
import gamestore.model.service.ShoppingCartProductServiceModel;
import gamestore.model.view.ProductViewModel;
import gamestore.model.view.ShoppingCartProductViewModel;
import gamestore.repository.ProductRepository;
import gamestore.repository.ShoppingCartProductRepository;
import gamestore.repository.ShoppingCartRepository;
import gamestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ShoppingCartProductRepository shoppingCartProductRepository;
    private final UserRepository userRepository;


    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ModelMapper modelMapper, ShoppingCartProductRepository shoppingCartProductRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.shoppingCartProductRepository = shoppingCartProductRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void addProductToCart(ShoppingCartProductServiceModel shoppingCartProductServiceModel) {
        Product product = this.productRepository
                .findById(shoppingCartProductServiceModel.getProductId())
                .orElseThrow();

        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findById(shoppingCartProductServiceModel.getShoppingCart().getId())
                .orElseThrow();

        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();


        if (this.shoppingCartProductRepository.count() == 0 || shoppingCart.getProducts().size() == 0) {
            shoppingCartProduct = this.modelMapper
                    .map(shoppingCartProductServiceModel, ShoppingCartProduct.class);


            shoppingCartProduct.setProduct(product);
            shoppingCartProduct.setShoppingCart(shoppingCart);


            shoppingCart.getProducts().add(shoppingCartProduct);

            this.shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);
            this.shoppingCartRepository.saveAndFlush(shoppingCart);
        } else {
            if (!this.shoppingCartProductRepository.findAll().contains(findByShoppingCardId(shoppingCartProductServiceModel.getShoppingCart().getId()))
                    && !this.shoppingCartProductRepository.findAll().contains(findByProductId(shoppingCartProductServiceModel.getProductId()))) {
                shoppingCartProduct = this.modelMapper
                        .map(shoppingCartProductServiceModel, ShoppingCartProduct.class);


                shoppingCartProduct.setProduct(product);
                shoppingCartProduct.setShoppingCart(shoppingCart);


                shoppingCart.getProducts().add(shoppingCartProduct);

                this.shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);
                this.shoppingCartRepository.saveAndFlush(shoppingCart);
            } else {
                shoppingCartProduct = this.shoppingCartProductRepository
                        .findByProductAndShoppingCart(product, shoppingCart)
                        .orElseThrow();
                shoppingCartProduct.setQuantity(shoppingCartProduct.getQuantity() + shoppingCartProductServiceModel.getQuantity());
                this.shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);
            }

        }


    }

    @Override
    public ShoppingCart findByUser(String username) {
        User user = this.userRepository
                .findByUsername(username)
                .orElse(null);

        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUser(user).orElse(null);

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            this.shoppingCartRepository.saveAndFlush(shoppingCart);
        }

        return shoppingCart;
    }

    @Override
    public List<ShoppingCartProductViewModel> findAllShoppingCartProducts(ShoppingCart shoppingCart) {
        List<ShoppingCartProduct> shoppingCartProducts = this.shoppingCartProductRepository.findAllByShoppingCart(shoppingCart);

        return shoppingCartProducts.stream().map(shoppingCartProduct -> {
            ShoppingCartProductViewModel shoppingCartProductViewModel = new ShoppingCartProductViewModel();
            ProductViewModel productViewModel = modelMapper.map(shoppingCartProduct.getProduct(), ProductViewModel.class);

            shoppingCartProductViewModel.setId(shoppingCartProduct.getId());
            shoppingCartProductViewModel.setQuantity(shoppingCartProduct.getQuantity());
            shoppingCartProductViewModel.setProduct(productViewModel);

            return shoppingCartProductViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void remove(String id) {
        ShoppingCartProduct shoppingCartProduct = this.shoppingCartProductRepository
                .findById(id)
                .orElseThrow();

        if (shoppingCartProduct.getQuantity() > 1) {
            shoppingCartProduct.setQuantity(shoppingCartProduct.getQuantity() - 1);
            this.shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);
        } else {
            this.shoppingCartProductRepository
                    .deleteById(id);
        }

    }

    @Override
    public ShoppingCartProduct findByShoppingCardId(String shoppingCartId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findById(shoppingCartId)
                .orElseThrow();

        ShoppingCartProduct shoppingCartProduct = this.shoppingCartProductRepository
                .findByShoppingCart(shoppingCart)
                .orElseThrow();

        return shoppingCartProduct;
    }

    @Override
    public BigDecimal total(ShoppingCart shoppingCart) {

        BigDecimal sum = new BigDecimal(0);

        for (int i = 0; i < shoppingCart.getProducts().size(); i++) {
            sum = sum.add(new BigDecimal(shoppingCart.getProducts().get(i).getQuantity()).multiply(shoppingCart.getProducts().get(i).getProduct().getPrice()));
        }

        return sum;
    }

    @Override
    public void removeAll() {
        this.shoppingCartProductRepository.findAll()
                .forEach(shoppingCartProduct -> {
                    shoppingCartProduct.getProduct().setQuantity(shoppingCartProduct.getProduct().getQuantity() - shoppingCartProduct.getQuantity());
                    this.productRepository.saveAndFlush(shoppingCartProduct.getProduct());
                });
        this.shoppingCartProductRepository
                .deleteAll();
    }

    @Override
    public ShoppingCartProduct findByProductId(String id) {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow();


        ShoppingCartProduct shoppingCartProduct = this.shoppingCartProductRepository
                .findByProduct(product)
                .orElseThrow();


        return shoppingCartProduct;
    }

}
