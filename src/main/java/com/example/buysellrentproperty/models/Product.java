package com.example.buysellrentproperty.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a product entity.
 */
@Entity
@NoArgsConstructor
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 10, max = 100)
    private String title;

    @NotNull(message = "Description cannot be null")
    @Size(max = 3000)
    @Column(length = 3000) // Установка максимальной длины колонки в базе данных
    private String description;

    @Positive(message = "Price must be positive")
    @Max(value = 100000000, message = "Price cannot exceed 100 million")
    private Integer price;

    private String city;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private Long previewImageId;
    @Getter(onMethod_ = {@JsonFormat(pattern = "dd.MM.yyyy HH:mm")})
    private LocalDateTime dateOfCreated;

    /**
     * Called before persisting a new product to set the creation date.
     */
    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now();
    }

    /**
     * Adds an image to the product's list of images.
     *
     * @param image The image to be added.
     */
    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }

}

