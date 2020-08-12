package gamestore.model.service;

import gamestore.model.entity.ShoppingCart;

public class ShoppingCartProductServiceModel extends BaseServiceModel {
    private String productId;
    private int quantity;
    private ShoppingCart shoppingCart;

    public ShoppingCartProductServiceModel() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
