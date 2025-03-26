package com.elberjsn.restaurant.models;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    
    private String name;

    private String address;
    
    private String phone;
    private String email;
    
    
    private String cnpj;
    
    private LocalTime opening;
    
    private LocalTime closed;

    
    private String password;
    
    private String site;
    private String typeKitchen;

   

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Employee> employee;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board> board;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reserve> reserves;





}
