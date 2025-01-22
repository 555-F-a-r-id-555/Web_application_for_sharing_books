package com.example.Account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPageAccess() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk()); // Ожидаем, что страница логина доступна
    }

    @Test
    public void testRegisterPageAccess() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk()); // Проверяем доступность страницы регистрации
    }

    @Test
    @WithMockUser(username = "user", roles = "USER") // Мокаем авторизованного пользователя
    public void testAuthenticatedAccessToAccountPage() throws Exception {
        mockMvc.perform(get("/account"))
                .andExpect(status().isOk()); // Проверяем, что авторизованный пользователь может зайти на /account
    }

}
