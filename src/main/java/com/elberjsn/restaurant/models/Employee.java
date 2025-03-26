package com.elberjsn.restaurant.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(unique = true)
    private String cpf;
    private String password;
    private LocalDate dtBirth;
    @NonNull
    private String phone;
    private String position;
    private LocalDate dtAdmission;
    private Double salary;
    private Boolean online;

    @ManyToOne
    @JoinColumn(name ="restaurant_id")
    private Restaurant restaurant;

}
