package com.example.jwt.application.usecase.product;

public interface AddCartItemUseCase {
    void execute(int userId, short quantity, int productId);
}