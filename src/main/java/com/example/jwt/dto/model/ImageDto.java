package com.example.jwt.dto.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ImageDto {
    private int id;
    private String src;
    private String alt;
    private byte position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
