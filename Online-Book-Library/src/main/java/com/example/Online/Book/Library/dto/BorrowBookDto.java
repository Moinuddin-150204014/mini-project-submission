package com.example.Online.Book.Library.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookDto {
    private Long borrowId;
    private Long bookId;
    private Long userId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
}
