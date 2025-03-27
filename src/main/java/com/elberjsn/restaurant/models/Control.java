package com.elberjsn.restaurant.models;

import java.time.LocalDateTime;
import java.util.List;

import com.elberjsn.restaurant.models.utils.StatusControl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data

public class Control {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;
    private LocalDateTime dtOpen;
    private LocalDateTime dtClosed;
    private StatusControl status;
    private Double totalValue;
    private String paymentMethod;

    @OneToOne(mappedBy = "control",fetch = FetchType.LAZY)
    private Reserve reserve;

    @OneToMany(mappedBy = "control",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Consumption> consumptions;

}
