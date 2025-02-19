package com.example.jwt.application.usecase.product.impl;

import org.apache.logging.log4j.util.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.jwt.application.usecase.product.AddCartItemUseCase;
import com.example.jwt.dto.request.CartItemRequest;
import com.example.jwt.services.CartRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddCartItemUseCaseImpl implements AddCartItemUseCase {

    private final CartRedisService cartRedisService;
    private final Logger logger = LoggerFactory.getLogger(AddCartItemUseCaseImpl.class);

    @Override
    public void execute(CartItemRequest cartItemRequest) {
        try {
            cartRedisService.addProductToCart(cartItemRequest);
        } catch (Exception e) {
            logger.error("Error occurred while adding product to cart: {}", e);
            throw new InternalException("Failed to add product to cart. Please try again later.", e);
        }
    }

}
