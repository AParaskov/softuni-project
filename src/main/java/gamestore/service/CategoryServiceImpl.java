package gamestore.service;

import gamestore.model.entity.Category;
import gamestore.model.entity.CategoryName;
import gamestore.model.service.CategoryServiceModel;
import gamestore.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCategories() {
        if (this.categoryRepository.count() == 0) {
            Arrays.stream(CategoryName.values())
                    .forEach(categoryName -> {
                        this.categoryRepository
                                .save(new Category(categoryName,
                                        String.format("Description for %s",
                                                categoryName.name())));
                    });
        }
    }

    @Override
    public CategoryServiceModel findByCategoryName(CategoryName categoryName) {
        return this.categoryRepository
                .findByName(categoryName)
                .map(category -> this.modelMapper.map(category, CategoryServiceModel.class))
                .orElse(null);
    }
}
