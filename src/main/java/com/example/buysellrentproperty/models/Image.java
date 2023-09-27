package com.example.buysellrentproperty.models;

import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents an image entity that can be associated with a product.
 * Представляет сущность изображения, которая может быть связана с продуктом.
 */
@Entity
@NoArgsConstructor
@Table(name = "images")
public class Image {
    /**
     * Unique identifier of the image.
     * Уникальный идентификатор изображения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Size(max = 255)
    private String originalFileName;

    @NotNull
    private Long size;

    @NotNull
    @Size(max = 100)
    private String contentType;

    private boolean previewImage;

    @Lob
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Product product;

    /**
     * Returns the unique identifier of the image.
     * Возвращает уникальный идентификатор изображения.
     *
     * @return The image's ID. Идентификатор изображения.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the image.
     * Устанавливает уникальный идентификатор изображения.
     *
     * @param id The new ID to set. Новый идентификатор для установки.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the image.
     * Возвращает название изображения.
     *
     * @return The image's name. Название изображения.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the image.
     * Устанавливает название изображения.
     *
     * @param name The new name to set. Новое название для установки.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the original filename of the image.
     * Возвращает исходное имя файла изображения.
     *
     * @return The original filename. Исходное имя файла.
     */
    public String getOriginalFileName() {
        return originalFileName;
    }

    /**
     * Sets the original filename of the image.
     * Устанавливает исходное имя файла изображения.
     *
     * @param originalFileName The new original filename to set. Новое исходное имя файла для установки.
     */
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    /**
     * Returns the size of the image in bytes.
     * Возвращает размер изображения в байтах.
     *
     * @return The image's size. Размер изображения.
     */
    public Long getSize() {
        return size;
    }

    /**
     * Sets the size of the image in bytes.
     * Устанавливает размер изображения в байтах.
     *
     * @param size The new size to set. Новый размер для установки.
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * Returns the content type of the image.
     * Возвращает тип содержимого изображения.
     *
     * @return The image's content type. Тип содержимого изображения.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type of the image.
     * Устанавливает тип содержимого изображения.
     *
     * @param contentType The new content type to set. Новый тип содержимого для установки.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Checks if the image is a preview image.
     * Проверяет, является ли изображение превью.
     *
     * @return True if the image is a preview image, false otherwise. True, если изображение является превью, в противном случае - false.
     */
    public boolean isPreviewImage() {
        return previewImage;
    }

    /**
     * Sets whether the image is a preview image.
     * Устанавливает, является ли изображение превью.
     *
     * @param previewImage True if the image is a preview image, false otherwise. True, если изображение является превью, в противном случае - false.
     */
    public void setPreviewImage(boolean previewImage) {
        this.previewImage = previewImage;
    }

    /**
     * Returns the byte array representing the image data.
     * Возвращает массив байт, представляющий данные изображения.
     *
     * @return The image data as a byte array. Данные изображения в виде массива байт.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Sets the byte array representing the image data.
     * Устанавливает массив байт, представляющий данные изображения.
     *
     * @param bytes The new image data byte array to set. Новый массив байт данных изображения для установки.
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Returns the associated product of the image.
     * Возвращает связанный с изображением продукт.
     *
     * @return The associated product. Связанный продукт.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the associated product of the image.
     * Устанавливает связанный с изображением продукт.
     *
     * @param product The new associated product to set. Новый связанный продукт для установки.
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}




//
//
//
//
//
//
//
//
//
//
///**
// * Represents an image entity that can be associated with a product.
// */
//@Entity
//@NoArgsConstructor
//@Table(name = "images")
//public class Image {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull
//    @Size(min = 2, max = 100)
//    private String name;
//
//    @NotNull
//    @Size(max = 255)
//    private String originalFileName;
//
//    @NotNull
//    private Long size;
//
//    @NotNull
//    @Size(max = 100)
//    private String contentType;
//
//    private boolean previewImage;
//
//    @Lob
//    private byte[] bytes;
//
//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    private Product product;
//
//    /**
//     * Returns the unique identifier of the image.
//     *
//     * @return The image's ID.
//     */
//    public Long getId() {
//        return id;
//    }
//
//    /**
//     * Sets the unique identifier of the image.
//     *
//     * @param id The new ID to set.
//     */
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    /**
//     * Returns the name of the image.
//     *
//     * @return The image's name.
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Sets the name of the image.
//     *
//     * @param name The new name to set.
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * Returns the original filename of the image.
//     *
//     * @return The original filename.
//     */
//    public String getOriginalFileName() {
//        return originalFileName;
//    }
//
//    /**
//     * Sets the original filename of the image.
//     *
//     * @param originalFileName The new original filename to set.
//     */
//    public void setOriginalFileName(String originalFileName) {
//        this.originalFileName = originalFileName;
//    }
//
//    /**
//     * Returns the size of the image in bytes.
//     *
//     * @return The image's size.
//     */
//    public Long getSize() {
//        return size;
//    }
//
//    /**
//     * Sets the size of the image in bytes.
//     *
//     * @param size The new size to set.
//     */
//    public void setSize(Long size) {
//        this.size = size;
//    }
//
//    /**
//     * Returns the content type of the image.
//     *
//     * @return The image's content type.
//     */
//    public String getContentType() {
//        return contentType;
//    }
//
//    /**
//     * Sets the content type of the image.
//     *
//     * @param contentType The new content type to set.
//     */
//    public void setContentType(String contentType) {
//        this.contentType = contentType;
//    }
//
//    /**
//     * Checks if the image is a preview image.
//     *
//     * @return True if the image is a preview image, false otherwise.
//     */
//    public boolean isPreviewImage() {
//        return previewImage;
//    }
//
//    /**
//     * Sets whether the image is a preview image.
//     *
//     * @param previewImage True if the image is a preview image, false otherwise.
//     */
//    public void setPreviewImage(boolean previewImage) {
//        this.previewImage = previewImage;
//    }
//
//    /**
//     * Returns the byte array representing the image data.
//     *
//     * @return The image data as a byte array.
//     */
//    public byte[] getBytes() {
//        return bytes;
//    }
//
//    /**
//     * Sets the byte array representing the image data.
//     *
//     * @param bytes The new image data byte array to set.
//     */
//    public void setBytes(byte[] bytes) {
//        this.bytes = bytes;
//    }
//
//    /**
//     * Returns the associated product of the image.
//     *
//     * @return The associated product.
//     */
//    public Product getProduct() {
//        return product;
//    }
//
//    /**
//     * Sets the associated product of the image.
//     *
//     * @param product The new associated product to set.
//     */
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//}
//
//
//
//
//
