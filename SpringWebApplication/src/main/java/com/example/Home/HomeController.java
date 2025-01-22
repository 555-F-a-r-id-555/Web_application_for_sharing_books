package com.example.Home;

import com.example.Registration.model.User;
import com.example.Registration.service.UserService;
import com.example.FilesForUpload.model.Book;
import com.example.FilesForUpload.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public HomeController(
            BookService bookService,
            UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        // Получаем книги
        List<Book> books = (List<Book>) bookService.getAllBooks();
        model.addAttribute("books", books);

        // Получаем текущего пользователя, если он залогинен
        Optional<User> user = userService.getCurrentUser();
        user.ifPresent(u -> model.addAttribute("user", u));


        return "home";
    }

    @GetMapping("/")
    public String getBooks(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model) {
        List<Book> books;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            books = bookService.searchBooks(searchTerm);
        } else {
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        model.addAttribute("searchTerm", searchTerm);
        return "home";
    }
}

//    @GetMapping("/home")
//    public String showHomePage(
//            @RequestParam(value = "searchTerm", required = false) String searchTerm,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "3") int size,
//            Model model
//    ) {
//        // Убедимся, что size не равен null или некорректному значению
//        if (size <= 0) {
//            size = 3;  // Значение по умолчанию
//        }
//
//        // Получаем страницу книг
//        Page<Book> bookPage = bookService.getAllBooks(page, size);
//
//        // Добавляем данные в модель
//        model.addAttribute("books", bookPage.getContent()); // Список книг на текущей странице
//        model.addAttribute("totalPages", bookPage.getTotalPages()); // Общее количество страниц
//        model.addAttribute("currentPage", page); // Текущая страница
//
//        // Получаем текущего пользователя, если он залогинен
//        Optional<User> user = userService.getCurrentUser();
//        user.ifPresent(u -> model.addAttribute("user", u));
//
//        return "home"; // Отображаем страницу home
//    }