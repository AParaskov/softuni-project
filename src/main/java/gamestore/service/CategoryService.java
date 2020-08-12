package gamestore.service;

import gamestore.model.entity.CategoryName;
import gamestore.model.service.CategoryServiceModel;

public interface CategoryService {
    void initCategories();

    CategoryServiceModel findByCategoryName(CategoryName categoryName);
}
