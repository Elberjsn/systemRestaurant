package com.elberjsn.restaurant.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data

public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    private int quantity;
    private String status;
    private String observations;
    private Double valueUnit;
    private Double valueTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product item;

    @ManyToOne
    @JoinColumn(name ="control_id")
    private Control control;


}
