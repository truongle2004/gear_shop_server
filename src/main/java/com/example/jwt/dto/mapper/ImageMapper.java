package com.example.jwt.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.jwt.dto.model.ImageDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ImageMapper {
    private ModelMapper mapper;

    public ImageDto mapToDto(com.example.jwt.entities.ImagesEntity image) {
        return mapper.map(image, ImageDto.class);
    }
}
