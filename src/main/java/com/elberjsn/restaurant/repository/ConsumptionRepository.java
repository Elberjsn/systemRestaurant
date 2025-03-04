package com.elberjsn.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elberjsn.restaurant.models.Consumption;


public interface ConsumptionRepository extends JpaRepository<Consumption,Long> {
    List<Consumption> findByRestaurantId(Long restaurant);
    List<Consumption> findByControlId(Long control);
}
