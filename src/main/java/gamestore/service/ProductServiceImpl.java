package gamestore.service;

import gamestore.model.entity.Product;
import gamestore.model.service.CategoryServiceModel;
import gamestore.model.service.ProductServiceModel;
import gamestore.model.view.ProductViewModel;
import gamestore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public void addProduct(ProductServiceModel productServiceModel) {
        CategoryServiceModel categoryServiceModel = this.categoryService
                .findByCategoryName(productServiceModel.getCategory().getCategoryName());

        productServiceModel.setCategory(categoryServiceModel);

        this.productRepository.saveAndFlush(this.modelMapper
                .map(productServiceModel, Product.class));
    }

    @Override
    public List<ProductViewModel> findAllProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setImgUrl(String.format("/img/%s-%s.jpg", product.getName(),
                            product.getCategory().getName().name()));

                    return productViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductViewModel findById(String id) {
        return this.productRepository
                .findById(id)
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setImgUrl(String.format("/img/%s-%s.jpg", product.getName(),
                            product.getCategory().getName().name()));

                    return productViewModel;
                })
                .orElse(null);
    }

    @Override
    public void updateProduct(String name, int quantity) {
        Product product = this.productRepository
                .findByName(name)
                .orElse(null);

        product.setQuantity(quantity + product.getQuantity());
        this.productRepository.saveAndFlush(product);
    }

    @Override
    public void delete(String id) {
        this.productRepository
                .deleteById(id);
    }

    @Override
    public List<ProductViewModel> findAllByCategoryDescription(String description) {
        return this.productRepository
                .findAllByCategory_Description(description)
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setImgUrl(String.format("/img/%s-%s.jpg", product.getName(),
                            product.getCategory().getName().name()));

                    return productViewModel;
                }).collect(Collectors.toList());

    }


}
