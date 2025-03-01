package com.elberjsn.restaurant.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String descrition;
    private String category;
    private Double price;

    @OneToOne(mappedBy = "item")
    private Consumption item;

    @OneToOne(mappedBy = "product")
    private Restaurant restaurant;

    
}
