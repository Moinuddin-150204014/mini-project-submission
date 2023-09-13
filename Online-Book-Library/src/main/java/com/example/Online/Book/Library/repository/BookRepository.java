package com.example.Online.Book.Library.repository;

import com.example.Online.Book.Library.entity.BookEntity;
import com.example.Online.Book.Library.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    BookEntity findByBookId(Long bookId);
    List<ReviewEntity> findAllByBookId(Long bookId);
    List<BookEntity> findAllByStatusNot(String status);
}