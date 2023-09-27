package com.example.buysellrentproperty.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private String city;
    private Long previewImageId;
    private List<ImageDTO> images; // Добавляем поле для хранения изображений
    private UserDTO user; // Добавляем поле для хранения информации о пользователе

    // Добавляем методы get и set для images и user
    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
