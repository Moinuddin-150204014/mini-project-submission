package com.example.Online.Book.Library.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookEntity bookEntity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;


    private String review;
    private float rating;
}
