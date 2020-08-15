package gamestore.ShoppingCartTest;

import gamestore.model.entity.Product;
import gamestore.model.entity.ShoppingCart;
import gamestore.model.entity.ShoppingCartProduct;
import gamestore.model.entity.User;
import gamestore.model.service.ShoppingCartProductServiceModel;
import gamestore.model.view.ShoppingCartProductViewModel;
import gamestore.repository.ProductRepository;
import gamestore.repository.ShoppingCartProductRepository;
import gamestore.repository.ShoppingCartRepository;
import gamestore.repository.UserRepository;
import gamestore.service.ShoppingCartService;
import gamestore.service.ShoppingCartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    private ShoppingCartService serviceToTest;

    @Mock
    private ShoppingCartProductRepository mockShoppingCartProductRepository;

    @Mock
    private ShoppingCartRepository mockShoppingCartRepository;

    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private UserRepository mockUserRepository;

    private ModelMapper modelMapper = new ModelMapper();
    private ShoppingCart shoppingCart;
    private ShoppingCartProduct shoppingCartProduct;
    private Product product;
    private User user;


    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("user");

        product = new Product();
        product.setId("UUID");

        shoppingCartProduct = new ShoppingCartProduct();
        shoppingCartProduct.setProduct(product);
        shoppingCartProduct.setId("shcid");
        shoppingCartProduct.setQuantity(5);

        shoppingCart = new ShoppingCart();
        shoppingCart.setId("SHC_UUID");
        shoppingCart.setProducts(new ArrayList<>());
        shoppingCart.getProducts().add(shoppingCartProduct);


        serviceToTest = new ShoppingCartServiceImpl(mockShoppingCartRepository, mockProductRepository, modelMapper, mockShoppingCartProductRepository, mockUserRepository);
    }


    @Test
    public void testRemove() {
        when(mockShoppingCartProductRepository.findById("shcid")).
                thenReturn(Optional.of(shoppingCartProduct));

        serviceToTest.remove("shcid");

        Assertions.assertEquals(4, shoppingCartProduct.getQuantity());

    }

    @Test
    public void testFindByUser() {
        when(mockUserRepository.findByUsername("user"))
                .thenReturn(Optional.of(user));


        when(mockShoppingCartRepository.findByUser(user))
                .thenReturn(Optional.of(shoppingCart));

        ShoppingCart actualShoppingCart = serviceToTest.findByUser("user");


        Assertions.assertEquals(shoppingCart.getId(), actualShoppingCart.getId());

    }

    @Test
    public void testFindAllShoppingCartProducts() {

        when(mockShoppingCartProductRepository.findAllByShoppingCart(shoppingCart)).
                thenReturn(List.of(shoppingCartProduct));

        List<ShoppingCartProductViewModel> actual = serviceToTest.findAllShoppingCartProducts(shoppingCart);

        Assertions.assertEquals(1, actual.size());
        int actualQuantity = actual.get(0).getQuantity();
        Assertions.assertEquals(shoppingCartProduct.getQuantity(), actualQuantity);

    }

    @Test
    public void testAddProductToCart() {
        ShoppingCartProductServiceModel shoppingCartProductServiceModel = new ShoppingCartProductServiceModel();
        shoppingCartProductServiceModel.setProductId(product.getId());
        shoppingCartProductServiceModel.setShoppingCart(shoppingCart);
        shoppingCartProductServiceModel.setQuantity(5);

        when(mockProductRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        when(mockShoppingCartRepository.findById(shoppingCart.getId()))
                .thenReturn(Optional.of(shoppingCart));

        serviceToTest.addProductToCart(shoppingCartProductServiceModel);

        Assertions.assertEquals(shoppingCartProduct.getProduct().getId(), shoppingCartProductServiceModel.getProductId());
    }


}
