package com.example.buysellrentproperty.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsLoader extends UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}