package com.example.jwt.dto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.jwt.entities.ImagesEntity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private boolean available;
    private String handle;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Description is required")
    private String description;
    private String tags;
    private List<ImagesEntity> images;
    private String vendor;
    private BigDecimal price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
