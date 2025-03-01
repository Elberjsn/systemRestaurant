package com.elberjsn.restaurant.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data

public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dtReserve;
    private LocalTime hrStart;
    private LocalDate hrEnd;
    private String status;
    private int quantity;
    private String obs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id",referencedColumnName = "id")
    private Board board;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "control_id",referencedColumnName = "id")
    private Control control;

}
