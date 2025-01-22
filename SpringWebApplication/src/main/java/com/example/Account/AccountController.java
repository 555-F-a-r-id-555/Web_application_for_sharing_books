package com.example.Account;

import com.example.FilesForUpload.model.Book;
import com.example.Registration.model.User;
import com.example.Registration.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AccountController {

    private final UserService userService;
    private final UserBookService userBookService;

    public AccountController(
            UserService userService,
            UserBookService userBookService
    ) {
        this.userService = userService;
        this.userBookService = userBookService;
    }

    @GetMapping("/account")
    public String showAccountPage(Model model) {
        // Получаем текущего пользователя
        User currentUser = userService.getCurrentUser()
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Получаем список книг, загруженных пользователем
        List<Book> books = userBookService.getBooksByCurrentUser();

        // Добавляем данные в модель
        model.addAttribute("user", currentUser);
        model.addAttribute("books", books);

        return "account";
    }

    @PostMapping("/account/deleteBook")
    public String deleteBookFromAccount(@RequestParam Long bookId, RedirectAttributes redirectAttributes) {
        try {
            userBookService.deleteBook(bookId);
            redirectAttributes.addFlashAttribute("message", "Книга успешно удалена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении книги: " + e.getMessage());
        }
        return "redirect:/account";
    }
}

