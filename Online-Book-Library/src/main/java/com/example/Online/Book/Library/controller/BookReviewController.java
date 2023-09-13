package com.example.Online.Book.Library.controller;

import com.example.Online.Book.Library.dto.ReviewDto;
import com.example.Online.Book.Library.service.serviceImpl.BookReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class BookReviewController {
    @Autowired
    private BookReviewServiceImpl bookReviewServiceImpl;
    @GetMapping("/books/{bookId}/reviews")
    public ResponseEntity<?> allReview(@PathVariable Long bookId) {
        try {
            List<ReviewDto> review =  bookReviewServiceImpl.allReview(bookId);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/books/{bookId}/reviews/create")
    public ResponseEntity<?> createReview(@PathVariable Long bookId, @RequestBody ReviewDto bookReviewDto) {
        try {
            ReviewDto newReview =  bookReviewServiceImpl.createReview(bookId, bookReviewDto);
            return new ResponseEntity<>(newReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/books/{bookId}/reviews/{reviewId}/update")
    public ResponseEntity<?> updateReview (@PathVariable Long bookId, @PathVariable Long reviewId, @RequestBody ReviewDto bookReviewDto) {
        try {
            ReviewDto updatedReview =  bookReviewServiceImpl.updateReview(bookId, reviewId, bookReviewDto);
            return new  ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/books/{bookId}/reviews/{reviewId}/delete")
    public ResponseEntity<?> deleteReview (@PathVariable Long bookId, @PathVariable Long reviewId) {
        try {
            bookReviewServiceImpl.deleteReview(bookId, reviewId);
            return new  ResponseEntity<>("Delete done", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
