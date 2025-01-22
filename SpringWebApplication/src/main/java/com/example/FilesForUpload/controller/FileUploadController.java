package com.example.FilesForUpload.controller;



import com.example.Account.UserBookService;
import com.example.FilesForUpload.model.Book;
import com.example.FilesForUpload.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    private final BookService bookService;
    private final UserBookService userBookService;

    public FileUploadController(
            BookService bookService
            ,UserBookService userBookService
    ) {
        this.bookService = bookService;
        this.userBookService = userBookService;
    }


    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(
            String bookName,
            String bookAuthor,
            Integer pages,
            String year,
            MultipartFile bookImage,
            MultipartFile bookFile,
            Model model
    ) {
        try {
            Book book = bookService.saveBook(
                    bookName,
                    bookAuthor,
                    pages,
                    year,
                    bookImage,
                    bookFile);

            model.addAttribute("message", "Книга успешно загружена и сохранена!");
            model.addAttribute("book", book);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка при загрузке: " + e.getMessage());
        }

        // Добавляем связь между пользователем и книгой
        userBookService.addBookForUser(bookService.getCurrentBook(bookName));
        return "upload";
    }

}
