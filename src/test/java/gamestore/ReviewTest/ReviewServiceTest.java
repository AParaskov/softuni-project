package gamestore.ReviewTest;

import gamestore.model.entity.Product;
import gamestore.model.entity.Review;
import gamestore.model.service.ReviewServiceModel;
import gamestore.repository.ProductRepository;
import gamestore.repository.ReviewRepository;
import gamestore.service.ReviewService;
import gamestore.service.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    private ReviewService serviceToTest;
    private Review review;
    private Product product;

    @Mock
    private ReviewRepository mockReviewRepository;

    @Mock
    private ProductRepository mockProductRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId("UUID");
        product.setQuantity(4);


        review = new Review();
        review.setProduct(product);
        review.setText("text");
        review.setAddedOn(LocalDateTime.now());
        review.setUserName("user");


        serviceToTest = new ReviewServiceImpl(mockReviewRepository, mockProductRepository, modelMapper);
    }

    @Test
    public void testAddReview() {
        ReviewServiceModel reviewServiceModel = modelMapper.map(review, ReviewServiceModel.class);
        when(mockProductRepository.findById(reviewServiceModel.getProductId()))
                .thenReturn(Optional.of(product));

        serviceToTest.addReview(reviewServiceModel);

        Assertions.assertEquals(review.getText(), reviewServiceModel.getText());
    }
}
