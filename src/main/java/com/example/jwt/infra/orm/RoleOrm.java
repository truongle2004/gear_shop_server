package com.example.jwt.infra.orm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleEntity
 */
@Getter()
@Setter()
@Entity(name = "roles")
public class RoleOrm {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private int id;
    private String name;
}
