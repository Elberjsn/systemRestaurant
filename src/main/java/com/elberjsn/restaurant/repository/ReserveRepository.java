package com.elberjsn.restaurant.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Reserve;


@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    @Query("SELECT b.number, r.hoursEnd FROM Reserve r JOIN r.board b "+
            "WHERE r.dtReserve = :dt AND r.hoursEnd BETWEEN :startOfDay AND :endOfDay")
    List<Object[]> reservesAndHoursEnd(@Param("dt") LocalDate dt ,@Param("startOfDay") LocalTime start,@Param("endOfDay") LocalTime end );


    List<Reserve> findByDtReserve(LocalDate dtReserve);
    
}
    
    
