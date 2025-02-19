package com.example.jwt.services;

import com.example.jwt.dto.request.CartItemRequest;

public interface CartRedisService {
    void addProductToCart(CartItemRequest cartItemRequest);

    void updateProductInCart(String userId, short quantity, int productId);
}
