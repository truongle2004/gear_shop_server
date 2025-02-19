package com.example.jwt.application.usecase.product.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.product.GetUserCartUseCase;
import com.example.jwt.dto.model.CartDto;
import com.example.jwt.dto.model.CartItemDto;
import com.example.jwt.dto.model.ImageDto;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.entities.CartEntity;
import com.example.jwt.entities.CartItemEntity;
import com.example.jwt.entities.ImagesEntity;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.infra.repositories.CartRepository;
import com.example.jwt.utils.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserCartUseCaseImpl implements GetUserCartUseCase {
    private final CartRepository cartRepository;
    private final Logger logger = LoggerFactory.getLogger(GetUserCartUseCaseImpl.class);

    @Override
    public CartDto execute(String userId) {
        try {

            CartEntity cart = this.cartRepository
                    .findByUserId(userId)
                    .orElseThrow(() -> new NotFoundException(
                            "You have no product in your cart. Please add some products to your cart."));

            Set<CartItemEntity> cartItems = cart.getCartItems();

            List<CartItemDto> cartItemDtos = cartItems.stream().map(cartItem -> {
                ProductEntity product = cartItem.getProduct();

                ImagesEntity firstImage = product.getImages()
                        .stream()
                        .filter(image -> image.getPosition() == AppConstants.FIRST_IMAGE_POSITION).toList().get(0);

                ImageDto imageDto = ImageDto.builder()
                        .id(firstImage.getId())
                        .src(firstImage.getSrc())
                        .alt(firstImage.getAlt())
                        .build();

                ProductDto productDto = ProductDto.builder()
                        .id(product.getId())
                        .slug(product.getSlug())
                        .images(List.of(imageDto))
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

        } catch (Exception e) {
            this.logger.error("Error occurred while retrieving cart: {}", e);
            throw new RuntimeException("Failed to fetch cart. Please try again later.", e);
        }
    }

}
