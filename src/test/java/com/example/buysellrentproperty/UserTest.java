package com.example.buysellrentproperty;

import com.example.buysellrentproperty.models.Product;
import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.models.enums.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testIsAdmin() {
        User user = new User();
        user.getRoles().add(Role.ROLE_ADMIN);

        boolean isAdmin = user.isAdmin();

        assertTrue(isAdmin);
    }

    @Test
    void testAddProductToUser() {
        User user = new User();
        Product product = new Product();

        user.addProductToUser(product);

        assertTrue(user.getProducts().contains(product));
        assertSame(user, product.getUser());
    }

    @Test
    void testIsAdminWithAdminRole() {
        User user = new User();
        user.getRoles().add(Role.ROLE_ADMIN);

        boolean isAdmin = user.isAdmin();

        assertTrue(isAdmin);
    }

    @Test
    void testIsAdminWithoutAdminRole() {
        User user = new User();

        boolean isAdmin = user.isAdmin();

        assertFalse(isAdmin);
    }



}

