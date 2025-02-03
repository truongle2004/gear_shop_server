package com.example.jwt.application.usecase.product.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.product.GetUserCartUseCase;
import com.example.jwt.dto.model.CartDto;
import com.example.jwt.dto.model.CartItemDto;
import com.example.jwt.dto.model.ImageDto;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.entities.CartEntity;
import com.example.jwt.entities.CartItemEntity;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.infra.repositories.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserCartCartUseCaseImpl implements GetUserCartUseCase {
    private final CartRepository cartRepository;

    @Override
    public CartDto execute(int userId) {
        CartEntity cart = this.cartRepository
                .findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        "You have no product in your cart. Please add some products to your cart."));

        Set<CartItemEntity> cartItems = cart.getCartItems();

        List<CartItemDto> cartItemDtos = cartItems.stream().map(cartItem -> {
            ProductEntity product = cartItem.getProduct();

            ProductDto productDto = ProductDto.builder()
                    .id(product.getId())
                    .slug(product.getSlug())
                    .images(product.getImages()
                            .stream()
                            .map(image -> ImageDto.builder()
                                    .id(image.getId())
                                    .src(image.getSrc())
                                    .alt(image.getAlt())
                                    .build())
                            .toList())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .build();

            return CartItemDto.builder()
                    .id(cartItem.getId())
                    .quantity(cartItem.getQuantity())
                    .product(productDto)
                    .build();

        }).toList();

        return CartDto.builder()
                .id(cart.getId())
                .totalPrice(cart.calculateTotalPrice())
                .cartItems(cartItemDtos)
                .build();

    }

}
