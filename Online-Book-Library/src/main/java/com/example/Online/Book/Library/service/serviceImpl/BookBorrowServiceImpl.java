package com.example.Online.Book.Library.service.serviceImpl;

import com.example.Online.Book.Library.dto.BorrowBookDto;
import com.example.Online.Book.Library.entity.BookEntity;
import com.example.Online.Book.Library.entity.BorrowBookEntity;
import com.example.Online.Book.Library.entity.UserEntity;
import com.example.Online.Book.Library.repository.BookRepository;
import com.example.Online.Book.Library.repository.BorrowBookRepository;
import com.example.Online.Book.Library.repository.UserRepository;
import com.example.Online.Book.Library.service.BookBorrowService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookBorrowServiceImpl implements BookBorrowService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BorrowBookRepository borrowBookRepository;

    @Override
    public BorrowBookDto borrowBook(Long bookId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());
        Long userId = user.get().getUserId();

        UserEntity userEntity = userRepository.findByUserId(userId);
        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        if (bookEntity == null || bookEntity.getStatus().equals("DELETED")) throw new Exception("Book not found!");
        if (bookEntity.getStatus().equals("BORROWED")) throw new Exception("Currently not available");

        BorrowBookEntity borrowBookEntity = new BorrowBookEntity();
        borrowBookEntity.setBookEntity(bookEntity);
        borrowBookEntity.setUserEntity(userEntity);
        borrowBookEntity.setBorrowDate(LocalDate.now());
        borrowBookEntity.setReturnDate(LocalDate.now());
        bookEntity.setStatus("BORROWED");

        BorrowBookEntity borrowBook = borrowBookRepository.save(borrowBookEntity);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(borrowBook, BorrowBookDto.class);
    }

    @Override
    public BorrowBookDto returnBook(Long bookId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> userEntity = userRepository.findByEmail(authentication.getName());
        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        if (bookEntity == null || bookEntity.getStatus().equals("DELETED")) throw new Exception("Book not found");

        BorrowBookEntity borrowBookEntity = borrowBookRepository.findByUserEntityAndBookEntity(userEntity, bookEntity);

        if (borrowBookEntity != null) {
            borrowBookRepository.delete(borrowBookEntity);
            bookEntity.setStatus("AVAILABLE");
            bookRepository.save(bookEntity);
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(borrowBookEntity, BorrowBookDto.class);
    }

    @Override
    public List<BookEntity> getAllBookByUser(Long userId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());

        String userRole = user.get().getRole();
        Long id = user.get().getUserId();

        if (!id.equals(userId) && userRole.equals("CUSTOMER")) {
            throw new Exception("You can't access this");
        }
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new Exception("User not found");

        List<BorrowBookEntity> borrowBooks = borrowBookRepository.findAllByUserEntity(userEntity);

        return borrowBooks.stream()
                .map(BorrowBookEntity::getBookEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookEntity> getAllBorrowedBookByUser(Long userId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());

        String userRole = user.get().getRole();
        Long id = user.get().getUserId();

        if (!id.equals(userId) && userRole.equals("CUSTOMER")) {
            throw new Exception("You can't access this");
        }
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new Exception("User not found");


//Incomplete
        return null;

    }

}
