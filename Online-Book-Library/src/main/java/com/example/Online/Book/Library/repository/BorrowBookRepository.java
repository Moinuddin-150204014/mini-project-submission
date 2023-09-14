package com.example.Online.Book.Library.repository;

import com.example.Online.Book.Library.entity.BookEntity;
import com.example.Online.Book.Library.entity.BorrowBookEntity;
import com.example.Online.Book.Library.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBookEntity,Long> {
    BorrowBookEntity findByUserEntityAndBookEntity(Optional<UserEntity> userEntity, BookEntity bookEntity);
    List<BorrowBookEntity> findAllByUserEntityAndReturnDateIsNull(UserEntity userEntity);

    List<BorrowBookEntity> findAllByUserEntity(UserEntity userEntity);
}
