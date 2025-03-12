package com.elberjsn.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
       
       
}
