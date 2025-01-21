package com.example.jwt.dto.model;

import lombok.Getter;

/**
 * RegisterDto
 */
@Getter()
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}
