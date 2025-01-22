package com.example.Account;


import com.example.FilesForUpload.model.Book;
import com.example.Registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    List<UserBook> findAllByUser(User user);

    Optional<UserBook> findByUserAndBook(User user, Book book);

    void deleteByBook(Book book);
}

