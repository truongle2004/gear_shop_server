package com.example.jwt.application.usecase.product.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.jwt.application.usecase.product.GetAllCategoryUseCase;
import com.example.jwt.dto.model.CategoryDto;
import com.example.jwt.entities.CategoryEntity;
import com.example.jwt.infra.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetAllCategoryUseCaseImpl implements GetAllCategoryUseCase {

    private final CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(GetAllCategoryUseCaseImpl.class);

    @Override
    public List<CategoryDto> execute() {
        try {
            List<CategoryEntity> categories = categoryRepository.findAll();
            return categories.stream()
                    .map(category -> {
                        CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName(),
                                category.getSlug());
                        return categoryDto;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all categories: {}", e.getMessage(), e);
            // TODO: customize exception
            throw new RuntimeException("Failed to fetch categories. Please try again later.", e);
        }
    }

}
