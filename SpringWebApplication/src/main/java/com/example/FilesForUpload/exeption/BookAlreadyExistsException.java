package com.example.FilesForUpload.exeption;


public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}

