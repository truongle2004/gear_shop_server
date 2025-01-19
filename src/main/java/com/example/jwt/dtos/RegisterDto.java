package com.example.jwt.dtos;

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
