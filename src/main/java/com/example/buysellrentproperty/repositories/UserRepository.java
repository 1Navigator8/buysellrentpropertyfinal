package com.example.buysellrentproperty.repositories;

import com.example.buysellrentproperty.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
}

