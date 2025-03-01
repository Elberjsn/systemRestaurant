package com.elberjsn.restaurant.models;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NonNull
    private String name;
    private String address;
    @NonNull
    private String phone;
    private String email;
    @NonNull
    private String cnpj;
    @NonNull
    private LocalTime opening;
    @NonNull
    private LocalTime closed;
    
    private String site;
    private String typeKitchen;

    @OneToOne(mappedBy = "restaurant")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;


}
