package gamestore.repository;

import gamestore.model.entity.ShoppingCart;
import gamestore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    Optional<ShoppingCart> findByUser(User user);

    Optional<ShoppingCart> findById(String id);
}
