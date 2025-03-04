package com.elberjsn.restaurant.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.models.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findByBoardAndDtReserveStartFalse(Board board, LocalDateTime dtReserveStart);

    @Query("SELECT r.board.id FROM Reserve r " +
            "JOIN r.board b " +
            "JOIN b.restaurant rest " +
            "WHERE rest.id = :restaurantId " +
            "AND (r.dtReservaInicio BETWEEN :startTime AND :endTime OR r.dtReservaFim BETWEEN :startTime AND :endTime)")
    List<Long> findBoardIdsByRestaurantIdAndBetween(
            @Param("restaurantId") Long restaurantId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
