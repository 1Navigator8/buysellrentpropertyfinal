package com.example.buysellrentproperty.repositories;

import com.example.buysellrentproperty.models.Image;
import com.example.buysellrentproperty.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductContaining(Product product);
}


