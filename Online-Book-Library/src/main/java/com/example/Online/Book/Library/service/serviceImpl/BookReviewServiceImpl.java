package com.example.Online.Book.Library.service.serviceImpl;

import com.example.Online.Book.Library.dto.ReviewDto;
import com.example.Online.Book.Library.entity.BookEntity;
import com.example.Online.Book.Library.entity.ReviewEntity;
import com.example.Online.Book.Library.entity.UserEntity;
import com.example.Online.Book.Library.repository.BookRepository;
import com.example.Online.Book.Library.repository.ReviewRepository;
import com.example.Online.Book.Library.repository.UserRepository;
import com.example.Online.Book.Library.service.BookReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookReviewServiceImpl implements BookReviewService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;

@Override
    public ReviewDto createReview(Long bookId, ReviewDto reviewDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());
        Long userId = user.get().getUserId();

        UserEntity userEntity = userRepository.findByUserId(userId);
        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        if (bookEntity == null || bookEntity.getStatus().equals("DELETED")) throw new Exception("Book does not find");
        ReviewEntity entity = reviewRepository.findByUserEntityAndBookEntity(userEntity, bookEntity);
        if (entity != null) throw new Exception("Review already given");

        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setBookEntity(bookEntity);
        reviewEntity.setUserEntity(userEntity);
        reviewEntity.setReview(reviewDto.getReview());
        reviewEntity.setRating(reviewDto.getRating());

        ReviewEntity review = reviewRepository.save(reviewEntity);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(review, ReviewDto.class);

    }

    @Override
    public List<ReviewDto> allReview(Long bookId) throws Exception {
        BookEntity bookEntity = bookRepository.findByBookId(bookId);
        ModelMapper modelMapper = new ModelMapper();
        List<ReviewEntity> bookReviews = bookRepository.findAllByBookId(bookEntity.getBookId());
        List<ReviewDto> bookReviewDtos = bookReviews.stream()
                .map(reviewEntity -> modelMapper.map(reviewEntity, ReviewDto.class))
                .collect(Collectors.toList());
        return bookReviewDtos;
    }

    @Override
    public ReviewDto updateReview(Long bookId, Long reviewId, ReviewDto bookReviewDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());
        Long userId = user.get().getUserId();

        UserEntity userEntity = userRepository.findByUserId(userId);
        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        if (bookEntity == null) throw new Exception("Book is not found");

        ReviewEntity bookReview = reviewRepository.findByReviewIdAndBookEntityAndUserEntity(reviewId, bookEntity, userEntity);
        if (bookReview == null) throw new Exception("No review found");

        bookReview.setReview(bookReviewDto.getReview());
        bookReview.setRating(bookReviewDto.getRating());

        reviewRepository.save(bookReview);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(bookReview, ReviewDto.class);
    }

    @Override
    public void deleteReview(Long bookId, Long reviewId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());
        Long userId = user.get().getUserId();

        UserEntity userEntity = userRepository.findByUserId(userId);
        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        if (bookEntity == null) throw new Exception("Book is not found");
        ReviewEntity bookReview = reviewRepository.findByReviewIdAndBookEntityAndUserEntity(reviewId, bookEntity, userEntity);
        if (bookReview == null) throw new Exception("No review found");
        reviewRepository.delete(bookReview);
    }
}
