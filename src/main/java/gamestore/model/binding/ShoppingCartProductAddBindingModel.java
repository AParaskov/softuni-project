package gamestore.model.binding;

public class ShoppingCartProductAddBindingModel {
    private String productId;
    private int quantity;

    public ShoppingCartProductAddBindingModel() {
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
}
