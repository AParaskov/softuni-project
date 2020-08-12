package gamestore.repository;

import gamestore.model.entity.Product;
import gamestore.model.entity.ShoppingCart;
import gamestore.model.entity.ShoppingCartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, String> {
    List<ShoppingCartProduct> findAllByShoppingCart(ShoppingCart shoppingCart);

    Optional<ShoppingCartProduct> findByProduct(Product product);

    Optional<ShoppingCartProduct> findByShoppingCart(ShoppingCart shoppingCart);

    Optional<ShoppingCartProduct> findByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
}
