package com.example.Online.Book.Library.service;

import com.example.Online.Book.Library.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBook();
    BookDto createBook(BookDto bookDto) throws Exception;
    void deleteBook(BookDto bookDto) throws Exception;
    BookDto updateBook(BookDto bookDto) throws Exception;

}