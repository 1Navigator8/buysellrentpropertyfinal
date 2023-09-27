package com.example.buysellrentproperty.configurations;

import com.example.buysellrentproperty.services.UserDetailsLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
/**
 * Security configuration class responsible for configuring
 * access control and authentication in the application.
 * Класс конфигурации безопасности, отвечающий за настройку
 * контроля доступа и аутентификации в приложении.
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsLoader userDetailsService;

    /**
     * Constructs a SecurityConfig instance with the provided userDetailsService.
     * Создает экземпляр SecurityConfig с предоставленным userDetailsService.
     *
     * param userDetailsService The service for loading user details.
     *                          Сервис для загрузки данных пользователя.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") // Защищаем доступ к Admin Dashboard только для пользователей с ролью "ADMIN"
                .antMatchers("/profile").authenticated() // Защищаем доступ к профилю только для авторизованных пользователей
                .antMatchers("/", "/product/**", "/images/**", "/registration", "/user/**", "/static/**", "/product/update/**")
                .permitAll() // Доступ без аутентификации
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());


        // Отключение временно конфигурации безопасности
//        http.csrf().disable()
//               .headers().frameOptions().disable();
    }
    /**
     * Configures authentication manager to use the provided userDetailsService and password encoder.
     * Настраивает менеджер аутентификации для использования предоставленного userDetailsService и кодировщика пароля.
     *
     * @param auth The AuthenticationManagerBuilder instance to configure.
     *             Экземпляр AuthenticationManagerBuilder для настройки.
     * @throws Exception If an error occurs during configuration.
     *                   Если произошла ошибка во время настройки.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    /**
     * Creates a PasswordEncoder bean for encoding passwords.
     * Создает бин PasswordEncoder для кодирования паролей.
     *
     * @return The configured PasswordEncoder instance.
     *         Настроенный экземпляр PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}


