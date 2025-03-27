package com.elberjsn.restaurant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.repository.BoardRepository;
import com.elberjsn.restaurant.repository.ReserveRepository;

@Service
public class ReserveService {


    @Autowired
    ReserveRepository reserveRepository;

    @Autowired
    RestaurantService restaurantService;
   
    @Autowired
    BoardRepository boardRepository;

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
    public Reserve findById(Long id){
        return reserveRepository.findById(id).orElse(null);
    }

    public List<Reserve> findByReservesToday(LocalDate today,Long id){
        return reserveRepository.findByDtReserveAndRestaurantId(today,id);
    }

    public List<Reserve> reserveWeek(LocalDate data,Long id){
        LocalDate start = data;
        LocalDate end = start.plusDays(7);
        
        return reserveRepository.findByDtReserveBetweenAndRestaurantId(start, end, id);
    }

    public Map<Integer,Integer> reserveBetweenDate(LocalDate data,LocalTime hour,Long id){
        hour.minusMinutes(30);
        LocalTime end = hour.plusMinutes(120);
        List<Reserve> reserves = findByReservesToday(data, id);
        System.out.println("ok");
        var listBoard =boardRepository.findByRestaurantId(id);

        List<Integer> allBoards =listBoard.stream().map(b -> b.getNumber()).collect(Collectors.toList());

        Map<Integer,Integer> boardsAll =listBoard.stream().collect((Collectors.toMap(Board::getNumber, Board::getCapacity)));

        for (Reserve reserve : reserves) {
            if (reserve.getHoursStart().isBefore(hour) && reserve.getHoursEnd().isAfter(end)) {
               if (allBoards.contains(reserve.getBoard().getNumber())) {
                    boardsAll.remove(reserve.getBoard().getNumber());
               }

            }
        }

        return boardsAll;

    }



}
