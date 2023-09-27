package com.example.buysellrentproperty.services;

import com.example.buysellrentproperty.models.Image;
import com.example.buysellrentproperty.models.Product;
import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.repositories.ImageRepository;
import com.example.buysellrentproperty.repositories.ProductRepository;
import com.example.buysellrentproperty.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ProductService {
    List<Product> listProducts(String title);

    void saveProduct(Principal principal, Product product,
                     MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException;

    User getUserByPrincipal(Principal principal);

    void deleteProduct(User user, Long id);

    void deleteImageFromProducts(Image image);

    void updateProduct(Product updatedProduct, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException;

    Product getProductById(Long id);
}