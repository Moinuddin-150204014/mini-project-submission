package com.example.Online.Book.Library.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private Long userId;
    private String email;
    private String token;
}