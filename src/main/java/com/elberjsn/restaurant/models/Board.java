package com.elberjsn.restaurant.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;
    private int capacity;
    private String type;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reserve> reserve;

    @OneToOne(mappedBy = "board")
    private Restaurant board;
}
