package com.elberjsn.restaurant.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Control;


@Repository
public interface ControlRepository extends JpaRepository<Control,Long>{
    Optional<Control> findByNumber(int number);
    Set<Control> findByDtClosedBetween(LocalDateTime start, LocalDateTime end);

}
