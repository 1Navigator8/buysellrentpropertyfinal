package com.example.buysellrentproperty.exeption;

import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @Autowired
    private UserService userService;



    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Проверка, что ответ не был отправлен
        if (!isResponseCommitted(request)) {
            // код обработки ошибок здесь
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                // Получаем текущего пользователя из Spring Security
                String currentUserName = authentication.getName();
                User currentUser = userService.findByUsername(currentUserName); // способ получения пользователя по имени
                model.addAttribute("user", currentUser); // Добавляем текущего пользователя к модели
            }
        }
        return "error-page"; // Перенаправляем на страницу ошибки с параметром "user"
    }

    // Метод для проверки, отправлен ли уже ответ
    private boolean isResponseCommitted(HttpServletRequest request) {
        return (request.getAttribute("javax.servlet.error.exception") != null ||
                request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR") != null);
    }
}