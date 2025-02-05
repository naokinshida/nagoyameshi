package com.example.nagoyameshi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findByStoreId(int storeId) {
        return reviewRepository.findByStoreId(storeId);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }
}