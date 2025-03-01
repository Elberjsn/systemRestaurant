package com.elberjsn.restaurant.models;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @NonNull
    private String cpf;
    private LocalDate dtBirth;
    @NonNull
    private String phone;
    private String cargo;
    private LocalDate dtAdmission;
    private Double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id",referencedColumnName = "id")
    private Restaurant restaurant;

}
