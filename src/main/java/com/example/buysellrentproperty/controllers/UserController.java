package com.example.buysellrentproperty.controllers;

import com.example.buysellrentproperty.exeption.UserAlreadyExistsException;
import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;

/**
 * Контроллер для управления действиями, связанными с пользователями.
 * Controller for handling actions related to users.
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Обработчик GET-запроса для страницы входа в систему.
     * Handler for GET request for the login page.
     *
     * @param principal объект, представляющий текущего пользователя (если авторизован)
     * @param model     объект для передачи данных в представление
     * @return имя представления для страницы входа в систему
     */

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user != null ? user : new User()); //  новый объект User вместо null, если пользователь не найден
        return "login";
    }

    /**
     * Обработчик GET-запроса для страницы профиля пользователя.
     * Handler for GET request for the user profile page.
     *
     * @param principal объект, представляющий текущего пользователя
     * @param model     объект для передачи данных в представление
     * @return имя представления для страницы профиля пользователя
     */
    @GetMapping("/profile")
    public String profile(Principal principal,
                          Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        // Логгирование значения атрибута user
        logger.info("Attribute 'user' value: {}", user);
        return "profile";
    }

    /**
     * Обработчик GET-запроса для страницы регистрации нового пользователя.
     * Handler for GET request for the registration page.
     *
     * @param principal объект, представляющий текущего пользователя
     * @param model     объект для передачи данных в представление
     * @return имя представления для страницы регистрации нового пользователя
     */
    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user != null ? user : new User()); // Аналогично, используется новый объект User вместо null
        return "registration";
    }

    /**
     * Обработчик POST-запроса для создания нового пользователя.
     * Handler for POST request to create a new user.
     *
     * @param user  объект пользователя для создания
     * @param model объект для передачи данных в представление
     * @return имя представления для страницы регистрации или перенаправление на страницу входа
     */
    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        try {
            if (!userService.createUser(user)) {
                model.addAttribute("errorMessage", "User with that email: " + user.getEmail() + " already exists");
                return "registration";
            }
        } catch (UserAlreadyExistsException e) {
            // Handle the exception appropriately, e.g., show an error page or redirect
        }
        return "redirect:/login";
    }

    /**
     * Обработчик GET-запроса для страницы с информацией о пользователе.
     * Handler for GET request for the user information page.
     *
     * @param user      объект пользователя, чья информация будет отображена
     * @param model     объект для передачи данных в представление
     * @param principal объект, представляющий текущего пользователя
     * @return имя представления для страницы с информацией о пользователе
     */
    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getProducts());
        // Логгирование значения атрибута user
        logger.info("Attribute 'user' value: {}", user);
        return "user-info";
    }
}


