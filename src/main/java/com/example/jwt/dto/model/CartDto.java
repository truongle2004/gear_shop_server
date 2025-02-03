package com.example.jwt.dto.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
    private int id;
    private BigDecimal totalPrice;
    private List<CartItemDto> cartItems;
}
