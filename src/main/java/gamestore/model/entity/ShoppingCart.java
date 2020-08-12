package gamestore.model.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart extends BaseEntity {
    private User user;
    private List<ShoppingCartProduct> products;

    public ShoppingCart() {
    }

    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "shoppingCart")
    public List<ShoppingCartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ShoppingCartProduct> products) {
        this.products = products;
    }

}


