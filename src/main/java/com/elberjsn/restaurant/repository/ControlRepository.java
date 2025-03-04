package com.elberjsn.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elberjsn.restaurant.models.Control;


public interface ControlRepository extends JpaRepository<Control,Long>{
    List<Control> findByConsumptionId(Long id);
    Optional<Control> findByNumber(int number);

}
