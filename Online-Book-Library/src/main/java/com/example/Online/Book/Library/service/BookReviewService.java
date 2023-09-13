package com.example.Online.Book.Library.service;

import com.example.Online.Book.Library.dto.ReviewDto;

import java.util.List;

public interface BookReviewService {

    public ReviewDto createReview(Long bookId, ReviewDto ReviewDto) throws Exception;
    public List<ReviewDto> allReview(Long bookId) throws Exception;

    public ReviewDto updateReview(Long bookId, Long reviewId, ReviewDto reviewDto) throws Exception;

    public void deleteReview(Long bookId, Long reviewId) throws Exception;

}