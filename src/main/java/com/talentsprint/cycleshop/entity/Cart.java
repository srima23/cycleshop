package com.talentsprint.cycleshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String brand;
    private int numBorrowed;

    
    
}
