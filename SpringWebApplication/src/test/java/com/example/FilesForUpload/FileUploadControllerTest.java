package com.example.FilesForUpload;

import com.example.Account.UserBookService;
import com.example.FilesForUpload.controller.FileUploadController;
import com.example.FilesForUpload.model.Book;
import com.example.FilesForUpload.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FileUploadControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserBookService userBookService;

    @Mock
    private Model model;

    @Mock
    private MultipartFile bookImage;

    @Mock
    private MultipartFile bookFile;

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleFileUpload_Success() throws Exception {
        // Arrange
        String bookName = "Test Book";
        String bookAuthor = "Test Author";
        Integer pages = 200;
        String year = "2025";

        Book book = new Book();
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);

        when(bookService.saveBook(eq(bookName), eq(bookAuthor), eq(pages), eq(year), eq(bookImage), eq(bookFile)))
                .thenReturn(book);
        when(bookService.getCurrentBook(eq(bookName))).thenReturn(book);


        String viewName = fileUploadController.handleFileUpload(
                bookName, bookAuthor, pages, year, bookImage, bookFile, model
        );


        assertEquals("upload", viewName);
        verify(bookService, times(1)).saveBook(eq(bookName), eq(bookAuthor), eq(pages), eq(year), eq(bookImage), eq(bookFile));
        verify(userBookService, times(1)).addBookForUser(book);
        verify(model, times(1)).addAttribute(eq("message"), eq("Книга успешно загружена и сохранена!"));
        verify(model, times(1)).addAttribute(eq("book"), eq(book));
    }

    @Test
    void testHandleFileUpload_Failure() throws Exception {
        String bookName = "Test Book";
        String bookAuthor = "Test Author";
        Integer pages = 200;
        String year = "2025";

        doThrow(new RuntimeException("Ошибка сохранения книги"))
                .when(bookService)
                .saveBook(eq(bookName), eq(bookAuthor), eq(pages), eq(year), eq(bookImage), eq(bookFile));


        String viewName = fileUploadController.handleFileUpload(
                bookName, bookAuthor, pages, year, bookImage, bookFile, model
        );


        assertEquals("upload", viewName);
        verify(bookService, times(1)).saveBook(eq(bookName), eq(bookAuthor), eq(pages), eq(year), eq(bookImage), eq(bookFile));
        verify(userBookService, never()).addBookForUser(any(Book.class));
        verify(model, times(1)).addAttribute(eq("message"), eq("Ошибка при загрузке: Ошибка сохранения книги"));
    }
}
