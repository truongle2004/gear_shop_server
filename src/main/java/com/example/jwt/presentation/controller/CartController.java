package com.example.jwt.presentation.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.product.AddCartItemUseCase;
import com.example.jwt.application.usecase.product.GetUserCartUseCase;
import com.example.jwt.dto.model.CartDto;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final GetUserCartUseCase getUserCartUseCase;
    private final AddCartItemUseCase addCartItemUseCase;

    @GetMapping()
    public ResponseEntity<CartDto> getUserCart(@RequestParam(value = "userId") int userId) {
        return new ResponseEntity<>(this.getUserCartUseCase.execute(userId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "quantity") short quantity,
            @RequestParam(value = "productId") int productId) {
        this.addCartItemUseCase.execute(userId, quantity, productId);
        return new ResponseEntity<>("Product added to cart", HttpStatus.CREATED);
    }
}
