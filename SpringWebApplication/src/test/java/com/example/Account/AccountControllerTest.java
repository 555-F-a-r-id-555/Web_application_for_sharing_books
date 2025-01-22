package com.example.Account;


import com.example.FilesForUpload.model.Book;

import com.example.Registration.model.User;
import com.example.Registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserBookService userBookService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private AccountController accountController;

    private User currentUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Инициализация текущего пользователя
        currentUser = new User();
        currentUser.setId(1L);
        currentUser.setUsername("testUser");
    }

    @Test
    void testShowAccountPage_Success() {
        // Arrange
        List<Book> books = List.of(new Book(), new Book());

        when(userService.getCurrentUser()).thenReturn(Optional.of(currentUser));
        when(userBookService.getBooksByCurrentUser()).thenReturn(books);

        // Act
        String viewName = accountController.showAccountPage(model);

        // Assert
        assertEquals("account", viewName);
        verify(userService, times(1)).getCurrentUser();
        verify(userBookService, times(1)).getBooksByCurrentUser();
        verify(model, times(1)).addAttribute("user", currentUser);
        verify(model, times(1)).addAttribute("books", books);
    }

    @Test
    void testShowAccountPage_UserNotFound() {
        // Arrange
        when(userService.getCurrentUser()).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountController.showAccountPage(model);
        });
        assertEquals("Пользователь не найден", exception.getMessage());
    }

    @Test
    void testDeleteBookFromAccount_Success() {
        // Arrange
        Long bookId = 1L;

        // Act
        String redirectUrl = accountController.deleteBookFromAccount(bookId, redirectAttributes);

        // Assert
        assertEquals("redirect:/account", redirectUrl);
        verify(userBookService, times(1)).deleteBook(bookId);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Книга успешно удалена!");
    }

    @Test
    void testDeleteBookFromAccount_Failure() {
        // Arrange
        Long bookId = 1L;
        String errorMessage = "Ошибка удаления книги";

        doThrow(new RuntimeException(errorMessage)).when(userBookService).deleteBook(bookId);

        // Act
        String redirectUrl = accountController.deleteBookFromAccount(bookId, redirectAttributes);

        // Assert
        assertEquals("redirect:/account", redirectUrl);
        verify(userBookService, times(1)).deleteBook(bookId);
        verify(redirectAttributes, times(1)).addFlashAttribute("error", "Ошибка при удалении книги: " + errorMessage);
    }
}
