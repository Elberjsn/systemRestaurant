package com.elberjsn.restaurant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.repository.BoardRepository;
import com.elberjsn.restaurant.repository.ReserveRepository;

@Service
public class ReserveService {

    private final BoardRepository boardRepository;

    @Autowired
    ReserveRepository reserveRepository;

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    BoardService boardService;

    ReserveService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Boolean verificHours(Reserve reserve, Long restaurant) {

        List<LocalTime> hours = restaurantService.openingHours(restaurant);

        return hours.contains(reserve.getHoursStart());

    }

    @Transactional
    public Reserve save(Reserve reserve, Long restaurant) {
        if (verificHours(reserve, restaurant)) {
            return reserveRepository.save(reserve);
        } else {
            return null;
        }
    }

    public List<Reserve> findReserveByDay(LocalDate dt) {

        return reserveRepository.findByDtReserve(dt);
    }

    public List<Integer> findBoardbyDate(LocalDate lc, Long restaurant) {

        List<Reserve> boardsReserveds = reserveRepository.findByDtReserve(lc);
        List<Integer> boardList = boardsReserveds.stream().map(br -> br.getBoard().getNumber())
                .collect(Collectors.toList());

        List<Integer> allBoards = boardService.allBoards(restaurant).stream()
                .map(r -> r.getNumber()).collect(Collectors.toList());

        return allBoards.stream().filter(b -> !boardList.contains(b)).toList();

    }

    public List<Integer> findBoardbyDateForReserve(LocalDate lc, Long restaurant, LocalTime start) {

        var playReserve = start.minusMinutes(30);
        var endReserve = start.plusHours(2);
        List<Object[]> boardsReserveds = reserveRepository.reservesAndHoursEnd(lc, playReserve, endReserve);

        List<Integer> allBoards = boardService.allBoards(restaurant).stream()
                .map(r -> r.getNumber()).collect(Collectors.toList());

        if (boardsReserveds.size() > 0) {
            ArrayList<Integer> freeBoards = new ArrayList<>();
            for (Object[] b : boardsReserveds) {
                LocalTime time = (LocalTime) b[1]; // hora final da reserva
                Integer board = (Integer) b[0];

                if (allBoards.contains(board) && time.isAfter(start)) {
                    freeBoards.add(board);
                }

            }

            return freeBoards;
        } else {
            return allBoards;
        }

    }

}
