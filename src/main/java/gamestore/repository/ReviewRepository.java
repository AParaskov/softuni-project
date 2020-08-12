package gamestore.repository;

import gamestore.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    void deleteByAddedOnBefore(LocalDateTime before);
}
