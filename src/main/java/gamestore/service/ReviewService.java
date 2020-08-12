package gamestore.service;

import gamestore.model.service.ReviewServiceModel;

public interface ReviewService {
    void addReview(ReviewServiceModel reviewServiceModel);

    void cleanOldReviews();
}
