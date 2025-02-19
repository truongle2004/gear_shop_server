package com.example.jwt.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemRequest {
    private String userId;
    private short quantity;
    private int productId;
}
