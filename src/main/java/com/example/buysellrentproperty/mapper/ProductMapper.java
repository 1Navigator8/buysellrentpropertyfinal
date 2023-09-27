package com.example.buysellrentproperty.mapper;

import com.example.buysellrentproperty.dto.ProductDTO;
import com.example.buysellrentproperty.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDTO convertToDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public List<ProductDTO> convertToDtoList(List<Product> products) {
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Добавьте этот метод для обновления сущности Product данными из ProductDTO
    public void updateProductFromDto(ProductDTO productDTO, Product product) {
        modelMapper.map(productDTO, product);
    }
}

