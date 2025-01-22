package com.example.Account;

public class UserBookExceptions {

    // Исключение: пользователь не найден
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    // Исключение: книга не найдена
    public static class BookNotFoundException extends RuntimeException {
        public BookNotFoundException(String message) {
            super(message);
        }
    }

    // Исключение: недостаточно прав
    public static class PermissionException extends RuntimeException {
        public PermissionException(String message) {
            super(message);
        }
    }
}

