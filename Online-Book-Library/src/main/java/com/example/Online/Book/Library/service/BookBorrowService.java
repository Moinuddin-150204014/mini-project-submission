package com.example.Online.Book.Library.service;

import com.example.Online.Book.Library.dto.BorrowBookDto;
import com.example.Online.Book.Library.entity.BookEntity;

import java.util.List;

public interface BookBorrowService {
    BorrowBookDto borrowBook(Long bookId) throws Exception;

    BorrowBookDto returnBook(Long bookId) throws Exception;

    List<BookEntity> getAllBookByUser(Long userId) throws Exception;

    List<BookEntity> getAllBorrowedBookByUser(Long userId) throws Exception;
}
