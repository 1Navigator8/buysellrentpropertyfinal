package com.example.buysellrentproperty.controllers;

import com.example.buysellrentproperty.models.Image;
import com.example.buysellrentproperty.repositories.ImageRepository;
import com.example.buysellrentproperty.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
/**
 * Controller class for handling image-related operations.
 * Класс контроллера для обработки операций, связанных с изображениями.
 */
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;
    private final ProductService productService;
    /**
     * Retrieves an image by its ID.
     * If the image is found, it is returned with appropriate headers.
     * If the image is not found, a "Not Found" response is returned.
     * Получает изображение по его идентификатору.
     * Если изображение найдено, оно возвращается с соответствующими заголовками.
     * Если изображение не найдено, возвращается ответ "Not Found".
     *
     * @param id The ID of the image to retrieve.
     *           Идентификатор изображения для получения.
     * @return A ResponseEntity with the image and appropriate headers if found,
     *         or a "Not Found" response if the image is not found.
     *         ResponseEntity с изображением и соответствующими заголовками, если найдено,
     *         или ответ "Not Found", если изображение не найдено.
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        // Получаем изображение из репозитория по его идентификатору
        Image image = imageRepository.findById(id).orElse(null);

        // Проверяем, было ли найдено изображение
        if (image == null) {
            return ResponseEntity.notFound().build(); // Отправляем ответ "Not Found"
        }
        if (image.getSize() == null) {
            // Handle the case where size is null
            // You might want to return an error response or handle it differently
            return ResponseEntity.badRequest().build();
        }

        // Создаем заголовки для ответа
        HttpHeaders headers = new HttpHeaders();
        headers.add("fileName", image.getOriginalFileName()); // Добавляем заголовок с именем файла
        headers.setContentType(MediaType.valueOf(image.getContentType())); // Устанавливаем тип контента
        headers.setContentLength(image.getSize()); // Устанавливаем размер контента

        // Возвращаем ResponseEntity с телом (изображением) и заголовками
        return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(image.getBytes())), headers, HttpStatus.OK);
    }


    /**
     * Handles DELETE request to delete an image by its ID.
     * Обрабатывает DELETE-запрос для удаления изображения по его идентификатору.
     *
     * @param id The ID of the image to delete.
     *           Идентификатор изображения для удаления.
     * @return ResponseEntity indicating the success of the operation or a "Not Found" response.
     *         ResponseEntity, указывающий на успешность операции или ответ "Not Found".
     */
    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        // Получаем изображение из репозитория по его идентификатору
        Image image = imageRepository.findById(id).orElse(null);

        // Проверяем, было ли найдено изображение
        if (image == null) {
            return ResponseEntity.notFound().build(); // Отправляем ответ "Not Found"
        }

        // Удаление изображения из связанных продуктов
        productService.deleteImageFromProducts(image);

        // Удаление изображения из базы данных
        imageRepository.delete(image);

        return ResponseEntity.ok().build(); // Отправляем ответ "OK"
    }
}