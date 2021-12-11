package com.blaj.beercatalogue.reviews.service;

import com.blaj.beercatalogue.reviews.model.Review;
import com.blaj.beercatalogue.reviews.repository.ReviewRepository;

import java.util.List;

public class ReviewService {
    public static float getBeerRating(String beerName) {
        ReviewRepository reviewRepository = ReviewRepository.getInstance();
        List<Review> reviewList = reviewRepository.getReviewList();

        float ratingSum = 0.0f;
        int ratingNum = 0;

        for (Review review : reviewList) {
            if (!review.getBeer().equals(beerName)) continue;

            ratingSum += Float.parseFloat(review.getRating());
            ratingNum ++;
        }

        if (ratingNum == 0) {
            return 0.0f;
        }

        return ratingSum / ratingNum;
    }
}
