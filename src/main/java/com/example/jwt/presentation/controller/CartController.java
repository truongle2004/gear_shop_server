package com.example.jwt.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    @GetMapping()
    public ResponseEntity<String> getUserCart() {
        return new ResponseEntity<>("cart", HttpStatus.OK);
    }
}
