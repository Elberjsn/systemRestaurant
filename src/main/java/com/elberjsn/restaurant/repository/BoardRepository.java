package com.elberjsn.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elberjsn.restaurant.models.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
       List<Board> findAllByRestaurantId(Long id);

       Optional<Board> findByNumberInRestaurantId(int number, Long id);
       
       
}
