package com.example.jwt.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.product.GetUserCartUseCase;
import com.example.jwt.dto.model.CartDto;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final GetUserCartUseCase getUserCartUseCase;

    @GetMapping()
    public ResponseEntity<CartDto> getUserCart(@RequestParam(value = "userId") int userId) {
        return new ResponseEntity<>(this.getUserCartUseCase.execute(userId), HttpStatus.OK);
    }
}
