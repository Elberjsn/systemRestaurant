package com.elberjsn.restaurant.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    @Query("SELECT b.number, r.hoursEnd FROM Reserve r JOIN r.board b " +
            "WHERE r.dtReserve = :dt AND r.hoursEnd BETWEEN :startOfDay AND :endOfDay")
    List<Object[]> reservesAndHoursEnd(@Param("dt") LocalDate dt, @Param("startOfDay") LocalTime start,
            @Param("endOfDay") LocalTime end);

    @EntityGraph(attributePaths = {"client","board"}) 
    List<Reserve> findByDtReserveAndRestaurantId(LocalDate dtReserve, Long id);

    List<Reserve> findByDtReserveBetweenAndRestaurantId(LocalDate start, LocalDate end, Long id);

    

}
