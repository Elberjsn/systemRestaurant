package com.elberjsn.restaurant.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReserveService reserveService;

    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> allBoards(Long idRestaurant) {

        return boardRepository.findByRestaurantId(idRestaurant);
    }

    @Transactional
    public void deleteBoard(Board board) {
        var deleteBoard = boardRepository.findById(board.getId()).get();
        saveBoard(deleteBoard);
    }

    public List<Board> boardsDisposable(Long idRestaurant, LocalDate today) {
        List<Reserve> reserves = reserveService.findByReservesToday(today, idRestaurant);

        List<Integer> numberBoardInReserves = reserves.stream()
                .map(b -> b.getBoard().getNumber())
                .collect(Collectors.toList());
        System.out.println("startProblem");
        var allBoardsRestaurant = allBoards(idRestaurant);
        System.out.println("endProblem");

        List<Board> boardsNotReserveds = allBoardsRestaurant.stream()
                .filter(board -> !numberBoardInReserves.contains(board.getNumber()))
                .collect(Collectors.toList());

        return boardsNotReserveds;
    }
}
