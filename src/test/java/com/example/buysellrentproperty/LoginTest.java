package com.example.buysellrentproperty;

import com.example.buysellrentproperty.controllers.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController controller;
    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Property search")));
    }

    @Test
    public void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin().user("elena@elena.com").password("123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredentials() throws Exception {
        mockMvc.perform(post("/login").param("user", "Semen"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    public void adminAccessTest() throws Exception {
        mockMvc.perform(get("/admin/somepage"))
                .andDo(print())
                .andExpect(status().is3xxRedirection()) // Ожидаем перенаправление
                .andExpect(redirectedUrl("http://localhost/login")); // Ожидаем перенаправление на страницу входа
    }

    @Test
    public void accessProfileAfterLogin() throws Exception {
        // Перед аутентификацией, доступ к профилю должен быть запрещен
        mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().is3xxRedirection()) // Ожидаем перенаправление
                .andExpect(redirectedUrl("http://localhost/login")); // Ожидаем перенаправление на страницу входа

        // Выполняем корректную аутентификацию
        mockMvc.perform(formLogin().user("elena@elena.com").password("123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Теперь, после успешной аутентификации, доступ к профилю должен быть разрешен
        mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());  //  ожидаемый статус на 302 (REDIRECT)
    }

  }

