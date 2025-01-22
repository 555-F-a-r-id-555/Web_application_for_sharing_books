package com.example.Registration.service;

import com.example.Registration.repository.UserRepository;
import com.example.Registration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean ifUserExist(String userName) {
        logger.info("Проверка, существует ли пользователь с именем: {}", userName);
        return userRepository.findByUsername(userName).isPresent();
    }

    public User registerUser(String userName, String password, String userRole) {
        logger.info("Регистрация нового пользователя: {}", userName);
        if (ifUserExist(userName)) {
            logger.error("Пользователь с именем {} уже существует", userName);
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(userRole);
        user.setUsername(userName);

        logger.info("Пользователь {} успешно зарегистрирован", userName);
        return userRepository.save(user);
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            logger.info("Получен текущий пользователь: {}", username);
            return userRepository.findByUsername(username);
        }

        logger.warn("Не удалось получить текущего пользователя");
        return Optional.empty();
    }
}


//import com.example.Registration.repository.UserRepository;
//import com.example.Registration.model.User;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//    public boolean ifUserExist(String userName){
//        return userRepository.findByUsername(userName).isPresent();
//    }
//
//
//    public User registerUser(String userName, String password, String userRole) {
//        if (ifUserExist(userName)){
//            throw new RuntimeException("User already exists");
//        }
//        // Сохранение пользователя
//        User user = new User();
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(userRole);
//        user.setUsername(userName);
//        return userRepository.save(user);
//    }
//
//    public Optional<User> getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated() &&
//                !"anonymousUser".equals(authentication.getPrincipal())) {
//            String username = authentication.getName();
//            return userRepository.findByUsername(username);
//        }
//
//        return Optional.empty();
//    }
//
//
//}
