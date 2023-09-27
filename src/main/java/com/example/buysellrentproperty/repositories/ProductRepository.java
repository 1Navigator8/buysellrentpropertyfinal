package com.example.buysellrentproperty.repositories;

import com.example.buysellrentproperty.models.Image;
import com.example.buysellrentproperty.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitle(String title);

    @Query("SELECT p FROM Product p JOIN p.images i WHERE :image MEMBER OF i")
    List<Product> findByImagesContaining(@Param("image") Image image);
}
