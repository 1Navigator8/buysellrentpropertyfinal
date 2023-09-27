package com.example.buysellrentproperty.models.enums;


import org.springframework.security.core.GrantedAuthority;


/**
 * Enum representing user roles in the application.
 * Перечисление, представляющее роли пользователей в приложении.
 */
public enum Role implements GrantedAuthority {

    /**
     * User role.
     * Роль пользователя.
     */
    ROLE_USER,

    /**
     * Administrator role.
     * Роль администратора.
     */
    ROLE_ADMIN;

    /**
     * Gets the authority of the role.
     * Возвращает авторитет роли.
     *
     * @return The authority name of the role.
     * Имя авторитета роли.
     */
    @Override
    public String getAuthority() {
        return name();
    }
}






//public enum Role implements GrantedAuthority {
//    ROLE_USER,
//    ROLE_ADMIN;
//
//    @Override
//    public String getAuthority() {
//        return name();
//    }
//}
//
