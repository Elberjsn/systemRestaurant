package com.elberjsn.restaurant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.repository.BoardRepository;


@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Transactional
    public Board saveBoard(Board board){
        return boardRepository.save(board);
    }
    public List<Board> allBoards(Long idRestaurant){
        return boardRepository.findByRestaurantId(idRestaurant).stream().collect(Collectors.toList());
    }
   
    @Transactional
    public void deleteBoard(Board board){
        var deleteBoard = boardRepository.findById(board.getId()).get();
        saveBoard(deleteBoard);
    }
}
