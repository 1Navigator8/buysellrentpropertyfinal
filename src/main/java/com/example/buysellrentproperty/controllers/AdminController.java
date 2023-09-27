package com.example.buysellrentproperty.controllers;

import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.models.enums.Role;
import com.example.buysellrentproperty.repositories.UserRepository;
import com.example.buysellrentproperty.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller class for handling admin-related operations.
 * Класс контроллера для обработки операций, связанных с администратором.
 */
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService; // Сервис для работы с пользователями
    private final UserRepository userRepository; // Репозиторий для работы с пользователями
    /**
     * Handles GET request for the admin page.
     * Displays a list of users and the current user.
     * Обрабатывает GET-запрос для страницы администратора.
     * Отображает список пользователей и текущего пользователя.
     *
     * @param model      The model to add attributes to.
     *                   Модель для добавления атрибутов.
     * @param principal  The principal representing the currently logged-in user.
     *                   Принципал, представляющий текущего авторизованного пользователя.
     * @return The view name "admin".
     *         Название представления "admin".
     */
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        // Получение списка пользователей и текущего пользователя
        List<User> users = userService.list();
        User loggedInUser = userService.getUserByPrincipal(principal);

        model.addAttribute("users", users);
        model.addAttribute("user", loggedInUser);
        return "admin"; // Возвращаем имя представления "admin"
    }

    /**
     * Handles POST request to ban a user by ID.
     * Обрабатывает POST-запрос для блокировки пользователя по ID.
     *
     * @param id The ID of the user to ban.
     *           Идентификатор пользователя для блокировки.
     * @return Redirects to the admin page.
     *         Перенаправляет на страницу администратора.
     */
//    @PostMapping("/admin/user/ban/{id}")
//    public String userBan(@PathVariable("id") Long id) {
//        userService.banUser(id);
//        return "redirect:/admin"; // Перенаправляем на страницу администратора
//    }

    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id") Long id, Principal principal, RedirectAttributes redirectAttributes) {
        // Получаем текущего пользователя
        User loggedInUser = userService.getUserByPrincipal(principal);

        // Получаем пользователя, которого пытаемся заблокировать
        User userToBan = userRepository.findById(id).orElse(null);

        // Проверяем, является ли текущий пользователь администратором и не пытается ли заблокировать самого себя
        if (loggedInUser != null && userToBan != null && loggedInUser.getId().equals(userToBan.getId())) {
            // Это попытка заблокировать самого себя - можно выполнить какие-то действия
            // Например, добавить сообщение об ошибке
            redirectAttributes.addFlashAttribute("error", "You can't block yourself.");
        } else {
            // Это не попытка заблокировать самого себя, выполняем блокировку
            userService.banUser(id);
        }

        return "redirect:/admin";
    }


    /**
     * Handles GET request to edit user data.
     * Displays an edit form with the current user's data.
     * Обрабатывает GET-запрос для редактирования данных пользователя.
     * Отображает форму редактирования с текущими данными пользователя.
     *
     * @param user      The User entity to edit.
     *                  Сущность пользователя для редактирования.
     * @param model     The model to add attributes to.
     *                  Модель для добавления атрибутов.
     * @param principal The principal representing the currently logged-in user.
     *                  Принципал, представляющий текущего авторизованного пользователя.
     * @return The view name "user-edit".
     *         Название представления "user-edit".
     */

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
        // Получение текущего пользователя и ролей
        User loggedInUser = userService.getUserByPrincipal(principal);
        Set<Role> userRoles = user.getRoles();

        model.addAttribute("user", user);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("roles", Role.values());
        model.addAttribute("userRoles", userRoles); // Добавляем атрибут с выбранными ролями

        return "user-edit"; // Возвращаем имя представления "user-edit"
    }

    /**
     * Handles POST request to save edited user data.
     * Обрабатывает POST-запрос для сохранения отредактированных данных пользователя.
     *
     * @param username The updated username.
     *                 Обновленное имя пользователя.
     * @param form     A map containing role information.
     *                 Карта, содержащая информацию о ролях.
     * @param user     The User entity to update.
     *                 Сущность пользователя для обновления.
     * @return Redirects to the admin page.
     *         Перенаправляет на страницу администратора.
     */
    @PostMapping("/admin/user/edit")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @ModelAttribute("userId") User user ) {

        // Обновляем имя пользователя
        user.setName(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        // Очищаем текущие роли пользователя и добавляем новые выбранные роли
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user); // Сохраняем обновленные данные пользователя

        return "redirect:/admin"; // Перенаправляем на страницу администратора
    }

    /**
     * Handles POST request to delete a user by ID.
     * Обрабатывает POST-запрос для удаления пользователя по ID.
     *
     * @param userId The ID of the user to delete.
     *               Идентификатор пользователя для удаления.
     * @return Redirects to the admin page.
     *         Перенаправляет на страницу администратора.
     */
    @PostMapping("/admin/user/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return "redirect:/admin"; // Перенаправляем на страницу администратора
        } catch (Exception e) {
            // Обработка исключения
            // Можно добавить логирование ошибки
            return "error-page"; // Перенаправляем на страницу ошибки
        }
    }
}

