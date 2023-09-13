package com.example.Online.Book.Library.dto;

import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookDto {
    private Long borrowId;
    private Long bookId;
    private Long userId;
    private Date dueDate;
    private Date returnDate;
}
