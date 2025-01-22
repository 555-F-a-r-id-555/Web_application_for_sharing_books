package com.example.FilesForUpload.service;

import com.example.Account.UserBookRepository;
import com.example.Cloud.YandexCloudService;
import com.example.FilesForUpload.exeption.BookAlreadyExistsException;
import com.example.FilesForUpload.model.Book;
import com.example.FilesForUpload.repository.BookRepository;
import com.example.Registration.repository.UserRepository;
import com.example.Registration.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final YandexCloudService yandexCloudService;
    private final BookRepository bookRepository;
    private UserRepository userRepository;
    private final UserService userService;
    private final UserBookRepository userBookRepository;


    @Autowired
    public BookService(
            YandexCloudService yandexCloudService,
            BookRepository bookRepository,
            UserService userService,
            UserBookRepository userBookRepository
    ) {
        this.yandexCloudService = yandexCloudService;
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.userBookRepository = userBookRepository;
    }

    // Проверка существования книги
    public boolean ifBookExist(String bookName) {
        logger.debug("Проверка существования книги с названием '{}'", bookName);
        return bookRepository.findByBookName(bookName).isPresent();
    }

    // Сохранение книги
    public Book saveBook(
            String bookName,
            String bookAuthor,
            Integer pages,
            String year,
            MultipartFile bookImage,
            MultipartFile bookFile
    ) throws IOException {
        validateBookData(bookName, pages, bookImage, bookFile);

        if (ifBookExist(bookName)) {
            logger.warn("Книга с названием '{}' уже существует", bookName);
            throw new BookAlreadyExistsException("Книга с таким названием уже существует: " + bookName);
        }

        try {
            // Асинхронная загрузка файлов
            CompletableFuture<String> bookImageFuture = yandexCloudService.uploadFile(bookImage);
            CompletableFuture<String> bookFileFuture = yandexCloudService.uploadFile(bookFile);

            // Ожидание завершения всех загрузок
            CompletableFuture.allOf(bookImageFuture, bookFileFuture).join();

            // Получение результатов загрузки
            String bookImageUrl = bookImageFuture.get();
            String bookFileUrl = bookFileFuture.get();

            logger.info("Файлы книги '{}' успешно загружены: imageUrl={}, fileUrl={}", bookName, bookImageUrl, bookFileUrl);

            // Создание и сохранение книги
            Book book = prepareBook(bookName, bookAuthor, pages, year, bookImageUrl, bookFileUrl);
            Book savedBook = bookRepository.save(book);

            logger.info("Книга '{}' успешно сохранена в базе данных", bookName);
            return savedBook;

        } catch (Exception e) {
            logger.error("Ошибка при сохранении книги '{}': {}", bookName, e.getMessage(), e);
            throw new RuntimeException("Не удалось сохранить книгу: " + e.getMessage(), e);
        }
    }

    // Метод для подготовки объекта Book
    private Book prepareBook(String bookName, String bookAuthor, Integer pages, String year, String bookImageUrl, String bookFileUrl) {
        Book book = new Book();
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);
        book.setPages(pages);
        book.setYear(year);
        book.setBookImage(bookImageUrl);
        book.setBookUrl(bookFileUrl);
        return book;
    }

    // Валидация входных данных
    private void validateBookData(String bookName, Integer pages, MultipartFile bookImage, MultipartFile bookFile) {
        logger.debug("Валидация данных для книги: name={}, pages={}", bookName, pages);
        if (bookName == null || bookName.isEmpty()) {
            throw new IllegalArgumentException("Название книги не может быть пустым.");
        }
        if (pages == null || pages <= 0) {
            throw new IllegalArgumentException("Количество страниц должно быть больше 0.");
        }
        if (bookImage == null || bookImage.isEmpty()) {
            throw new IllegalArgumentException("Изображение книги не загружено.");
        }
        if (bookFile == null || bookFile.isEmpty()) {
            throw new IllegalArgumentException("Файл книги не загружен.");
        }
    }

    public List<Book> searchBooks(String searchTerm) {
        return bookRepository.findByBookNameContainingIgnoreCaseOrBookAuthorContainingIgnoreCase(searchTerm, searchTerm);
    }


    public List<Book> getAllBooks() {
        logger.debug("Получение всех книг из базы данных");
        return bookRepository.findAll();
    }

    public Book getCurrentBook(String bookName) {
        return bookRepository.findByBookName(bookName)
                .orElseThrow(() -> new IllegalArgumentException("Книга с ID " + bookName + " не найдена"));
    }


    //    public Page<Book> getAllBooks(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return bookRepository.findAll(pageable);
//    }
//
//    public Page<Book> searchBooks(String searchTerm, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return bookRepository.findByBookNameContainingIgnoreCase(searchTerm, pageable);
//    }

//    public List<Book> searchBooks(String searchTerm) {
//        return bookRepository.findByBookNameContainingOrBookAuthorContaining(searchTerm, searchTerm);
//    }

}





