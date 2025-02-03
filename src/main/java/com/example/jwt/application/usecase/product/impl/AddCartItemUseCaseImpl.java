package com.example.jwt.application.usecase.product.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.jwt.application.exceptions.NotFoundException;
import com.example.jwt.application.usecase.product.AddCartItemUseCase;
import com.example.jwt.entities.CartEntity;
import com.example.jwt.entities.CartItemEntity;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.infra.repositories.CartRepository;
import com.example.jwt.infra.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddCartItemUseCaseImpl implements AddCartItemUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public boolean execute(int userId, short quantity, int productId) {
        ProductEntity product = this.productRepository.findById((short) productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Optional<CartEntity> cart = this.cartRepository.findByUserId(userId);
        if (cart.isPresent()) {
            CartEntity cartEntity = cart.get();
            Set<CartItemEntity> cartItems = cartEntity.getCartItems();
            for (CartItemEntity cartItem : cartItems) {
                if (cartItem.getProduct().getId() == productId) {
                    cartItem.setQuantity((short) (cartItem.getQuantity() + quantity));
                    this.cartRepository.save(cartEntity);
                    return true;
                }
            }
            CartItemEntity cartItem = new CartItemEntity();
            cartItem.setProduct(product);
            cartItem.setQuantity((short) 1);
            cartEntity.getCartItems().add(cartItem);
            this.cartRepository.save(cartEntity);
            return true;
        }

        return false;
    }

}