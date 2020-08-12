package gamestore.ProductTest;

import gamestore.model.entity.Category;
import gamestore.model.entity.CategoryName;
import gamestore.model.entity.Product;
import gamestore.model.service.CategoryServiceModel;
import gamestore.model.service.ProductServiceModel;
import gamestore.model.view.ProductViewModel;
import gamestore.repository.ProductRepository;
import gamestore.service.CategoryService;
import gamestore.service.ProductService;
import gamestore.service.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private ProductService serviceToTest;
    private Product product;
    private Category category;
    private CategoryName categoryName = CategoryName.valueOf("GAMES");

    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private CategoryService categoryService;


    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {

        category = new Category();
        category.setName(categoryName);
        category.setDescription("description");

        product = new Product();
        product.setId("UUID");
        product.setQuantity(4);
        product.setName("product");
        product.setManufacturer("manufacturer");
        product.setCategory(category);

        serviceToTest = new ProductServiceImpl(mockProductRepository, modelMapper, categoryService);
    }

    @Test
    public void testAddProduct() {
        CategoryServiceModel categoryServiceModel = new CategoryServiceModel();
        ProductServiceModel productServiceModel = modelMapper.map(product, ProductServiceModel.class);

        when(categoryService.findByCategoryName(productServiceModel.getCategory().getCategoryName()))
                .thenReturn(categoryServiceModel);

        serviceToTest.addProduct(productServiceModel);

        Assertions.assertEquals(product.getName(), productServiceModel.getName());

    }

    @Test
    public void testFindAllProducts() {
        when(mockProductRepository.findAll())
                .thenReturn(List.of(product));

        List<ProductViewModel> actualProducts = serviceToTest.findAllProducts();

        Assertions.assertEquals(1, actualProducts.size());
        ProductViewModel productViewModel = actualProducts.get(0);
        Assertions.assertEquals(product.getName(), productViewModel.getName());
    }

    @Test
    public void testFindById() {
        when(mockProductRepository.findById("UUID"))
                .thenReturn(Optional.of(product));

        ProductViewModel actualProduct = serviceToTest.findById("UUID");

        Assertions.assertEquals(product.getName(), actualProduct.getName());
    }

    @Test
    public void testUpdateProduct() {
        int quantity = 5;
        when(mockProductRepository.findByName("product"))
                .thenReturn(Optional.of(product));

        serviceToTest.updateProduct("product", quantity);

        Assertions.assertEquals(9, product.getQuantity());
    }

    @Test
    public void testDelete() {
        serviceToTest.delete("UUID");

        Assertions.assertEquals(0, mockProductRepository.count());
    }

    @Test
    public void testFindAllByCategoryDescription() {
        when(mockProductRepository.findAllByCategory_Description("description"))
                .thenReturn(Optional.of(product));

        List<ProductViewModel> actualProducts = serviceToTest.findAllByCategoryDescription("description");

        Assertions.assertEquals(1, actualProducts.size());
        ProductViewModel productViewModel = actualProducts.get(0);
        Assertions.assertEquals(product.getName(), productViewModel.getName());
    }
}
