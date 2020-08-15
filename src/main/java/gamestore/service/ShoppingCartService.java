package gamestore.service;

import gamestore.model.entity.ShoppingCart;
import gamestore.model.service.ShoppingCartProductServiceModel;
import gamestore.model.view.ShoppingCartProductViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {
    void addProductToCart(ShoppingCartProductServiceModel shoppingCartProductServiceModel);

    ShoppingCart findByUser(String username);

    List<ShoppingCartProductViewModel> findAllShoppingCartProducts(ShoppingCart shoppingCart);

    void remove(String id);

    BigDecimal total(ShoppingCart shoppingCart);

    void removeAll(String id);
}
