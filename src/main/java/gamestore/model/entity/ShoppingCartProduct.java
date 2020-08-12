package gamestore.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart_products")
public class ShoppingCartProduct extends BaseEntity {
    private Product product;
    private int quantity;
    private ShoppingCart shoppingCart;

    public ShoppingCartProduct() {
    }

    @OneToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "fk_shopping_cart")
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
