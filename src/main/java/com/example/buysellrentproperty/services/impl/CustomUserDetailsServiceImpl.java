package com.example.buysellrentproperty.services.impl;

import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.repositories.UserRepository;
import com.example.buysellrentproperty.services.UserDetailsLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * Custom implementation of Spring Security's UserDetailsLoader.
 * Пользовательская реализация UserDetailsLoader для Spring Security.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsLoader {
    private final UserRepository userRepository;

    /**
     * Load user details by email.
     * Загружает детали пользователя по электронной почте.
     *
     * @param email The email of the user. Электронная почта пользователя.
     * @return UserDetails containing user information. UserDetails, содержащий информацию о пользователе.
     * @throws UsernameNotFoundException if the user is not found. если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return user;
    }
}