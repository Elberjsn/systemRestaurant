package com.elberjsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Control;
import java.util.List;
import java.time.LocalDateTime;


@Repository
public interface ControlRepository extends JpaRepository<Control,Long>{
    Optional<Control> findByNumber(int number);
    List<Control> findByDtClosedContaining(LocalDateTime start, LocalDateTime end);

}
