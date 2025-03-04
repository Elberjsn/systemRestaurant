package com.elberjsn.restaurant.service;

import java.util.List;

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
        return boardRepository.findAllByRestaurantId(idRestaurant);
    }
    public Board boardByNumberInRestaurant(int number,Long idRestaurant){
        return boardRepository.findByNumberInRestaurantId(number,idRestaurant).get();
    }

    @Transactional
    public Board editBoard(Board board){
        var edit_Board = boardRepository.findByNumberInRestaurantId(board.getNumber(), board.getRestaurant().getId()).get();
        edit_Board.setCapacity(board.getCapacity());
        edit_Board.setType(board.getType());
        
        return saveBoard(edit_Board);
    }
    
   
    @Transactional
    public void deleteBoard(Board board){
        var deleteBoard = boardRepository.findById(board.getId()).get();
        saveBoard(deleteBoard);
    }
}
