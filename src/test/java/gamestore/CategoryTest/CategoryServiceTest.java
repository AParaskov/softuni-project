package gamestore.CategoryTest;

import gamestore.model.entity.Category;
import gamestore.model.entity.CategoryName;
import gamestore.model.service.CategoryServiceModel;
import gamestore.repository.CategoryRepository;
import gamestore.service.CategoryService;
import gamestore.service.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private CategoryService serviceToTest;
    private Category category;
    private CategoryName categoryName;

    @Mock
    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {


        category = new Category();
        category.setName(categoryName);
        category.setDescription("asdgasg");


        serviceToTest = new CategoryServiceImpl(categoryRepository, modelMapper);

    }

    @Test
    public void testFindRoleByName() {
        when(categoryRepository.findByName(categoryName)).
                thenReturn(Optional.of(category));

        CategoryServiceModel categoryServiceModel = serviceToTest.findByCategoryName(categoryName);

        Assertions.assertEquals(category.getName(), categoryServiceModel.getCategoryName());

    }
}
