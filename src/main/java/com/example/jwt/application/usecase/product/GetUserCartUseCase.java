package com.example.jwt.application.usecase.product;

import com.example.jwt.dto.model.CartDto;

public interface GetUserCartUseCase {
    CartDto execute(int userId);
}
