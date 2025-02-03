package com.example.jwt.application.usecase.product;

public interface AddCartItemUseCase {
    boolean execute(int userId, short quantity, int productId);
}