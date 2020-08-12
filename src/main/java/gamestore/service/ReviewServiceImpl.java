package gamestore.service;

import gamestore.model.entity.Product;
import gamestore.model.entity.Review;
import gamestore.model.service.ReviewServiceModel;
import gamestore.repository.ProductRepository;
import gamestore.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addReview(ReviewServiceModel reviewServiceModel) {
        Product product = productRepository
                .findById(reviewServiceModel.getProductId())
                .orElseThrow();
        Review review = modelMapper
                .map(reviewServiceModel, Review.class);
        review.setProduct(product);

        reviewRepository.saveAndFlush(review);

    }

    @Override
    public void cleanOldReviews() {
        LocalDateTime endTime = LocalDateTime.now().minus(20, ChronoUnit.DAYS);
        reviewRepository.deleteByAddedOnBefore(endTime);
    }
}
