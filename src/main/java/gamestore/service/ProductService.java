package gamestore.service;

import gamestore.model.service.ProductServiceModel;
import gamestore.model.view.ProductViewModel;

import java.util.List;

public interface ProductService {
    void addProduct(ProductServiceModel productServiceModel);

    List<ProductViewModel> findAllProducts();

    ProductViewModel findById(String id);

    void updateProduct(String name, int quantity);

    void delete(String id);

    List<ProductViewModel> findAllByCategoryDescription(String description);


}
