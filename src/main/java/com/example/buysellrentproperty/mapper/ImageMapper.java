package com.example.buysellrentproperty.mapper;

import com.example.buysellrentproperty.dto.ImageDTO;
import com.example.buysellrentproperty.models.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageDTO convertToDto(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setName(image.getName());
        dto.setOriginalFileName(image.getOriginalFileName());
        dto.setSize(image.getSize());
        dto.setContentType(image.getContentType());
        dto.setPreviewImage(image.isPreviewImage());
        return dto;
    }

    public Image convertToEntity(ImageDTO dto) {
        Image image = new Image();
        image.setId(dto.getId());
        image.setName(dto.getName());
        image.setOriginalFileName(dto.getOriginalFileName());
        image.setSize(dto.getSize());
        image.setContentType(dto.getContentType());
        image.setPreviewImage(dto.isPreviewImage());
        return image;
    }
}
