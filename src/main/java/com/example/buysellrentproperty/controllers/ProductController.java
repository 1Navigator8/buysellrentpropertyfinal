package com.example.buysellrentproperty.controllers;

import com.example.buysellrentproperty.exeption.ProductExceptionHandler;
import com.example.buysellrentproperty.exeption.ResourceNotFoundException;
import com.example.buysellrentproperty.mapper.ProductMapper;
import com.example.buysellrentproperty.models.Product;
import com.example.buysellrentproperty.models.User;
import com.example.buysellrentproperty.services.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
/**
 * Controller class for handling product-related operations.
 * Класс контроллера для обработки операций, связанных с продуктами.
 */
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    /**
     * Handles GET request to display a list of products.
     * Обрабатывает GET-запрос для отображения списка продуктов.
     *
     * @param title     The search keyword for filtering products by title.
     *                  Ключевое слово для поиска продуктов по названию.
     * @param principal The principal representing the currently logged-in user.
     *                  Принципал, представляющий текущего авторизованного пользователя.
     * @param model     The model to add attributes to.
     *                  Модель для добавления атрибутов.
     * @return The view name "products".
     *         Название представления "products".
     */
    @GetMapping("/")
    public String products(@RequestParam(name = "searchWord", required = false) String title, Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user != null ? user : new User());
        model.addAttribute("products", productService.listProducts(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        return "products";
    }
    /**
     * Handles GET request to display product details by its ID.
     * Обрабатывает GET-запрос для отображения деталей продукта по его идентификатору.
     *
     * @param id        The ID of the product to display.
     *                  Идентификатор продукта для отображения.
     * @param model     The model to add attributes to.
     *                  Модель для добавления атрибутов.
     * @param principal The principal representing the currently logged-in user.
     *                  Принципал, представляющий текущего авторизованного пользователя.
     * @return The view name "product-info" or "products" if the product is not found.
     *         Название представления "product-info" или "products", если продукт не найден.
     */
    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("user", productService.getUserByPrincipal(principal));
            model.addAttribute("product", product);
            model.addAttribute("images", product.getImages());
            model.addAttribute("authorProduct", product.getUser());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
            String formattedDate = product.getDateOfCreated().format(formatter);
            model.addAttribute("formattedDate", formattedDate);
            return "product-info";
        } catch (ResourceNotFoundException e) {
            // Handle the exception appropriately, e.g., show an error page or redirect
            return "products"; // Replace with an appropriate view name
        }
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                Principal principal,
                                Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            // Передача ошибок в модель для отображения
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("errorMessages", "\n" +
                    "Please check the entered data.");
            return "my-products"; // Имя вашего шаблона для создания продукта
        }
        try {
            productService.saveProduct(principal, product, file1, file2, file3);
            return "redirect:/my/products";
        } catch (ResourceNotFoundException e) {
            // Handle the exception appropriately, e.g., show an error page or redirect
            return "error-page"; // Замените на подходящее имя представления для ошибки
        }
    }


    @PostMapping("/product/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute Product updatedProduct,
                                @RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                Principal principal, Model model) {
        try {
            // Проверка, принадлежит ли продукт текущему пользователю
            Product existingProduct = productService.getProductById(id);
            if (existingProduct == null || !existingProduct.getUser().getEmail().equals(principal.getName())) {
                return "redirect:/my/products"; // Перенаправление, если нет доступа к редактированию
            }

            productService.updateProduct(updatedProduct, file1, file2, file3);
            User user = productService.getUserByPrincipal(principal);
            model.addAttribute("user", user);

            return "redirect:/product/" + id; // Перенаправить на страницу продукта после обновления
        } catch (ResourceNotFoundException ex) {
            // Обрабатываем ошибку и добавляем сообщение об ошибке к модели
            model.addAttribute("errorMessage", "Product not found");
            return "error-page"; // Перенаправить на страницу ошибки
        } catch (IOException e) {
            // Обрабатываем ошибку и добавляем сообщение об ошибке к модели
            model.addAttribute("errorMessage", "IO Error");
            return "error-page"; // Перенаправить на страницу ошибки
        }
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "my-products";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        try {
            productService.deleteProduct(productService.getUserByPrincipal(principal), id);
            return "redirect:/my/products";
        } catch (ResourceNotFoundException | ProductExceptionHandler e) {
            // Handle the exception appropriately, e.g., show an error page or redirect
            return "error-page"; // Replace with an appropriate view name
        }
    }
}


