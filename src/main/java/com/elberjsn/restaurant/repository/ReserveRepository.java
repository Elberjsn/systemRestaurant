package com.elberjsn.restaurant.repository;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    Set<Reserve> findByDtReserveStartBetween(LocalDateTime start, LocalDateTime end);
    
}
    
    
