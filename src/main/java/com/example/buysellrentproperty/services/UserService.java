package com.example.buysellrentproperty.services;

import com.example.buysellrentproperty.models.User;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface UserService {
    boolean createUser(User user);
    List<User> list();
    void banUser(Long id);
    void changeUserRoles(User user, Map<String, String> form);
    User getUserByPrincipal(Principal principal);
    boolean deleteUser(Long userId);
    User findByUsername(String username);
}