package com.example.Registration.controller;


import com.example.Registration.controller.UserController; // Укажите правильный пакет
import com.example.Registration.model.User;
import com.example.Registration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService; // Мокируем зависимость

    @InjectMocks
    private UserController userController; // Тестируемый контроллер

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Инициализируем моки
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Настраиваем MockMvc
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        when(userService.ifUserExist("testuser")).thenReturn(false);
        when(userService.registerUser(anyString(), anyString(), eq("ROLE_USER"))).thenReturn(new User());

        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("password", "password123")
                        .with(csrf())) // Учитываем CSRF
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register?success"));

        verify(userService, times(1)).registerUser(anyString(), anyString(), eq("ROLE_USER")); // Проверка вызова метода
    }
}




