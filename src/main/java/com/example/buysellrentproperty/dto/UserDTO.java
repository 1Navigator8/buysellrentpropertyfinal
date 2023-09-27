package com.example.buysellrentproperty.dto;

import com.example.buysellrentproperty.models.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String phoneNumber;
    private String name;
    private ImageDTO avatar;
    private boolean active;
    private String activationCode;
    private Set<Role> roles;
    private LocalDateTime dateOfCreated;
}
