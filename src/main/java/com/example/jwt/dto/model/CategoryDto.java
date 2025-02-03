package com.example.jwt.dto.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryDto {
    private byte id;
    private String name;
    private String slug;
}
