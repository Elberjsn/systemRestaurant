package com.elberjsn.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Consumption;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption,Long> {
    List<Consumption> findByControlId(Long control);
}
