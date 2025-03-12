package com.elberjsn.restaurant.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    Optional<Reserve> findByDtReserveStartBetween(LocalDateTime start, LocalDateTime end);
}
    
    
