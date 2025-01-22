package com.example.Registration.service;

import com.example.Registration.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Получаем пользователя из базы данных через Optional
        return userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword()) // зашифрованный пароль
                        .roles(user.getRole()
                                .replace("ROLE_", "")
                        ) // роль пользователя
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));
    }


}