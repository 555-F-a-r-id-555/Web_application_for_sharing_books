package com.example.FilesForUpload.repository;

import com.example.FilesForUpload.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookName(String bookName);
    List<Book> findByBookNameContainingIgnoreCaseOrBookAuthorContainingIgnoreCase(String bookName, String bookAuthor);


    Page<Book> findByBookNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    List<Book> findByYear(String year);
    List<Book> findByPages(Integer pages);

    List<Book> findByBookNameContainingOrBookAuthorContaining(String searchTerm, String searchTerm1);

}
