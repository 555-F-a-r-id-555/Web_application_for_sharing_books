package com.example.Account;

import com.example.FilesForUpload.model.Book;
import com.example.FilesForUpload.repository.BookRepository;
import com.example.Registration.model.User;
import com.example.Registration.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserBookService {

    private static final Logger logger = LoggerFactory.getLogger(UserBookService.class);

    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;
    private final UserService userService;

    public UserBookService(
            UserBookRepository userBookRepository,
            UserService userService,
            BookRepository bookRepository) {
        this.userBookRepository = userBookRepository;
        this.userService = userService;
        this.bookRepository = bookRepository;
    }

    // Вспомогательный метод для получения текущего пользователя
    private User getCurrentUser() {
        return userService.getCurrentUser()
                .orElseThrow(() -> {
                    logger.error("Ошибка: текущий пользователь не найден");
                    return new UserBookExceptions.UserNotFoundException("Пользователь не найден");
                });
    }

    // Добавляем связь между пользователем и книгой
    public void addBookForUser(Book book) {
        try {
            logger.info("Начало добавления книги для текущего пользователя");

            User currentUser = getCurrentUser();

            UserBook userBook = new UserBook();
            userBook.setUser(currentUser);
            userBook.setBook(book);

            userBookRepository.save(userBook);
            logger.info("Книга успешно добавлена для пользователя: {}", currentUser.getUsername());
        } catch (Exception e) {
            logger.error("Ошибка при добавлении книги: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Получение списка книг, загруженных текущим пользователем
    public List<Book> getBooksByCurrentUser() {
        try {
            logger.info("Получение списка книг для текущего пользователя");

            User currentUser = getCurrentUser();

            List<UserBook> userBooks = userBookRepository.findAllByUser(currentUser);
            logger.info("Найдено {} книг для пользователя: {}", userBooks.size(), currentUser.getUsername());

            return userBooks.stream()
                    .map(UserBook::getBook)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Ошибка при получении списка книг: {}", e.getMessage(), e);
            throw e;
        }
    }

    // Удаление книги
    public void deleteBook(Long bookId) {
        try {
            logger.info("Попытка удаления книги с ID: {}", bookId);

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> {
                        logger.error("Книга с ID {} не найдена", bookId);
                        return new UserBookExceptions.BookNotFoundException("Книга с ID " + bookId + " не найдена");
                    });

            User currentUser = getCurrentUser();

            UserBook userBook = userBookRepository.findByUserAndBook(currentUser, book)
                    .orElseThrow(() -> {
                        logger.error("Пользователь {} не имеет прав на удаление книги с ID {}", currentUser.getUsername(), bookId);
                        return new UserBookExceptions.PermissionException("Вы не можете удалить эту книгу");
                    });

            userBookRepository.delete(userBook);
            bookRepository.delete(book);
            logger.info("Книга с ID {} успешно удалена для пользователя: {}", bookId, currentUser.getUsername());
        } catch (Exception e) {
            logger.error("Ошибка при удалении книги с ID {}: {}", bookId, e.getMessage(), e);
            throw e;
        }
    }
}

