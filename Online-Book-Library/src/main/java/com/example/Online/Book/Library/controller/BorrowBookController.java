package com.example.Online.Book.Library.controller;

import com.example.Online.Book.Library.dto.BorrowBookDto;
import com.example.Online.Book.Library.service.serviceImpl.BookBorrowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowBookController {
    @Autowired
    private BookBorrowServiceImpl bookBorrowServiceImpl;
    @GetMapping("/books/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId) {
        try {
            BorrowBookDto borrowBook = bookBorrowServiceImpl.borrowBook(bookId);
            return new  ResponseEntity<>(borrowBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
        try {
            BorrowBookDto borrowBook = bookBorrowServiceImpl.returnBook(bookId);
            return new  ResponseEntity<>(borrowBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
