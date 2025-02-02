package com.example.jwt.dto.model;

import java.math.BigDecimal;
import java.util.List;

import com.example.jwt.entities.ImagesEntity;

import lombok.Data;

@Data
public class SearchResponseDto {
    private int id;
    private String title;
    private BigDecimal price;
    private List<ImagesEntity> images;
}
