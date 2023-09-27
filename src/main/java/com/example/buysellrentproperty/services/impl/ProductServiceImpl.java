package com.example.buysellrentproperty.services.impl;

import com.example.buysellrentproperty.models.Image;
import com.example.buysellrentproperty.models.Product;
import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.repositories.ImageRepository;
import com.example.buysellrentproperty.repositories.ProductRepository;
import com.example.buysellrentproperty.repositories.UserRepository;
import com.example.buysellrentproperty.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    // Метод для получения списка продуктов с возможностью фильтрации по названию
    public List<Product> listProducts(String title) {
        if (title != null) {
            return productRepository.findByTitle(title);
        }
        return productRepository.findAll();
    }

    // Метод для сохранения продукта с изображениями
    public void saveProduct(Principal principal, Product product,
                            MultipartFile file1,
                            MultipartFile file2,
                            MultipartFile file3) throws IOException {
        User user = getUserByPrincipal(principal);
        product.setUser(user);

        // Добавляем изображения к продукту, если они предоставлены
        if (file1.getSize() > 0) {
            Image image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() > 0) {
            Image image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() > 0) {
            Image image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }

        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), user.getEmail());

        // Сохраняем продукт в репозитории
        Product savedProduct = productRepository.save(product);

        // Если у продукта есть изображения, устанавливаем идентификатор первого изображения в качестве превью
        if (!savedProduct.getImages().isEmpty()) {
            savedProduct.setPreviewImageId(savedProduct.getImages().get(0).getId());
            productRepository.save(savedProduct);
        }
    }

    // Метод для получения пользователя по Principal
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
    }

    // Преобразование MultipartFile в сущность Image
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    // Метод для удаления продукта
    public void deleteProduct(User user, Long id) {
        Product product = getProductById(id);
        if (product != null && product.getUser().getId().equals(user.getId())) {
            productRepository.delete(product);
            log.info("Product with id = {} was deleted", id);
        } else {
            log.error("Product with id = {} is not found or user doesn't have access", id);
        }
    }

    // Метод для обновления продукта с изображениями
    public void updateProduct(Product updatedProduct,
                              MultipartFile file1,
                              MultipartFile file2,
                              MultipartFile file3) {
        MultipartFile[] imageFiles = {file1, file2, file3};
        Product existingProduct = getProductById(updatedProduct.getId());

        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found");
        }

        // Обновляем данные продукта
        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCity(updatedProduct.getCity());
        existingProduct.setDescription(updatedProduct.getDescription());

        // Обновляем изображения, если предоставлены новые файлы
        for (MultipartFile file : imageFiles) {
            if (file.getSize() > 0) {
                try {
                    Image image = toImageEntity(file);
                    existingProduct.addImageToProduct(image);
                    if (existingProduct.getPreviewImageId() == null) {
                        // Если превью-изображение не установлено, устанавливаем первое добавленное изображение
                        existingProduct.setPreviewImageId(image.getId());
                    }
                } catch (IOException e) {
                    log.error("Error while processing file: " + e.getMessage());
                    // Здесь можно выполнить дополнительные действия, если требуется.
                }
            }
        }

        log.info("Updating Product. Title: {}; Author email: {}", existingProduct.getTitle(), existingProduct.getUser().getEmail());

        // Сохраняем обновленный продукт в репозитории
        productRepository.save(existingProduct);
    }

    // Метод для получения продукта по его идентификатору


    public void deleteImageFromProducts(Image image) {
        List<Product> products = productRepository.findByImagesContaining(image);

        for (Product product : products) {
            product.getImages().remove(image);
            productRepository.save(product);
        }

        // Удаление файла изображения из базы данных и файловой системы
        imageRepository.delete(image); // Удаление из базы данных
        deleteImageFile(image); // Удаление из файловой системы
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    private void deleteImageFile(Image image) {
        String fileName = image.getOriginalFileName();
        String imagePath = "/static/images/" + fileName;

        try {
            ClassPathResource resource = new ClassPathResource(imagePath);
            File file = resource.getFile();

            if (file.exists()) {
                if (file.delete()) {
                log.info("Image file deleted successfully: {}", fileName);
            } else {
                log.warn("Failed to delete image file: {}", fileName);
            }
            } else {
                log.warn("Image file not find: {}", fileName);
            }
        } catch (IOException e) {
            log.error("Error deleting image file: {}", fileName, e);
        }

    }
}
