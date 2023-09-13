package com.example.Online.Book.Library.controller;

import com.example.Online.Book.Library.dto.BookDto;
import com.example.Online.Book.Library.service.serviceImpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @PostMapping("/books/create")
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        try {
            BookDto newBook = bookServiceImpl.createBook(bookDto);
            return new  ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/books/update")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
        try {
            BookDto updatedBook = bookServiceImpl.updateBook(bookDto);
            return new  ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/books/delete")
    public ResponseEntity<?> deleteBook(@RequestBody BookDto bookDto) {
        try {
            bookServiceImpl.deleteBook(bookDto);
            return new  ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/all")
    public ResponseEntity<?> allBooks() {
        try {
            List<BookDto> allBook = bookServiceImpl.getAllBook();
            return new  ResponseEntity<>(allBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
