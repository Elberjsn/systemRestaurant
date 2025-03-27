package com.elberjsn.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Board;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
       
      
       List<Board> findByRestaurantId(Long restaurant);

       Board findByNumberAndRestaurantId(int number, Long id);
       
}
