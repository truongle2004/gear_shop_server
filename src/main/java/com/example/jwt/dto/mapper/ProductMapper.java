package com.example.jwt.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.entities.ProductEntity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductMapper {
    private final ModelMapper mapper;

    public ProductDto mapToDto(ProductEntity product) {
        return mapper.map(product, ProductDto.class);
    }

    public ProductEntity mapToEntity(ProductDto productDto) {
        return mapper.map(productDto, ProductEntity.class);
    }
}
