package com.elberjsn.restaurant.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reserve> reserve;

    @ManyToOne
    @JoinColumn(name = "restaurant_id",referencedColumnName = "id")
    private Restaurant restaurant;

    public List<Board> asList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'asList'");
    }
}
