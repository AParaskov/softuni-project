package gamestore.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReviewCleanupScheduler {

    private ReviewService reviewService;

    public ReviewCleanupScheduler(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Scheduled(cron = "${game-store.clean-up}")
    public void cleanUpOldReviews() {
        reviewService.cleanOldReviews();
    }
}
