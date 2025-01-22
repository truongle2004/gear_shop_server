package com.example.jwt.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.product.GetByCategoryUseCase;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.response.ObjectResponse;
import com.example.jwt.utils.AppConstants;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private GetByCategoryUseCase getByCategoryUseCase;

    @GetMapping
    public ResponseEntity<ObjectResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "category", required = false) String category) {
        return new ResponseEntity<>(this.getByCategoryUseCase.execute(pageNo, pageSize, sortBy, category),
                HttpStatus.OK);
    }
}
