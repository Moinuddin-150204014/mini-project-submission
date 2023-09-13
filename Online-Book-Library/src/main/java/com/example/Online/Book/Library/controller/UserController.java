package com.example.Online.Book.Library.controller;

import com.example.Online.Book.Library.constants.AppConstants;
import com.example.Online.Book.Library.dto.UserDto;
import com.example.Online.Book.Library.dto.UserLoginRequestModel;
import com.example.Online.Book.Library.entity.BookEntity;
import com.example.Online.Book.Library.service.serviceImpl.BookBorrowServiceImpl;
import com.example.Online.Book.Library.service.serviceImpl.UserServiceImpl;
import com.example.Online.Book.Library.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.attribute.HashPrintRequestAttributeSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private BookBorrowServiceImpl bookBorrowServiceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            UserDto newUser = userServiceImpl.createUser(userDto);
            String token = JWTUtils.generateToken(newUser.getEmail());
            Map<String, Object> register = new HashMap<>();
            register.put("user", newUser);
            register.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + token);
            return ResponseEntity.status(HttpStatus.CREATED).body(register);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestModel userLogin, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
            UserDto userDto = userServiceImpl.getUser(userLogin.getEmail());
            String token = JWTUtils.generateToken(userDto.getEmail());
            Map<String, Object> login = new HashMap<>();
            login.put("userId", userDto.getUserId());
            login.put("email", userDto.getEmail());
            login.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + token);
            return ResponseEntity.status(HttpStatus.OK).body(login);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Bad credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> userDetailsByUserId(@PathVariable Long userId) {
        try {
            UserDto userDto = userServiceImpl.getUserByUserId(userId);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userId}/books")
    public ResponseEntity<?> getAllBooks(@PathVariable Long userId) {
        try {
            List<BookEntity> allBook = bookBorrowServiceImpl.getAllBookByUser(userId);
            return new ResponseEntity<>(allBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userId}/borrowed-books")
    public ResponseEntity<?> getBorrowedBooks(@PathVariable Long userId) {
        try {
            List<BookEntity> allBorrowBook = bookBorrowServiceImpl.getAllBorrowedBookByUser(userId);
            return new ResponseEntity<>(allBorrowBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
