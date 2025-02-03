package com.example.jwt.dto.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {
    private int id;
    private int quantity;
    private ProductDto product;
}
