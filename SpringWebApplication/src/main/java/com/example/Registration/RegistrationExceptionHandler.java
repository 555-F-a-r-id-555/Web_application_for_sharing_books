package com.example.Registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class RegistrationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException ex, Model model) {
        logger.error("Произошла ошибка: {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, Model model) {
        logger.error("Непредвиденная ошибка: {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже.");
        return modelAndView;
    }
}

