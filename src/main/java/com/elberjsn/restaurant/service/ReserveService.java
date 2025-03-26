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
import com.elberjsn.restaurant.repository.ReserveRepository;

@Service
public class ReserveService {


    @Autowired
    ReserveRepository reserveRepository;

    @Autowired
    RestaurantService restaurantService;


    @Autowired
    BoardService boardService;

   

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


    public List<Reserve> findReserveByDayandHours(LocalDate dt,LocalTime start,LocalTime end,Long restaurant) {
       
        if (end == null) {
            end = start.plusHours(2);
        }
        if (!start.equals(LocalTime.MAX)) {
            start = start.minusMinutes(30);
        }

        return reserveRepository.findReservations(dt,start,end,restaurant);
    }


    public List<Integer> findBoardbyDateForReserve(LocalDate dateLocal, Long restaurant, LocalTime start) {

        var playReserve = start.minusMinutes(30);
        var endReserve = start.plusHours(2);
        System.out.println("restaurant= "+restaurant);

        restaurant = (long) 1;
        System.out.println("r== "+restaurant);

        List<Reserve> boardsReserveds = findReserveByDayandHours(dateLocal,playReserve,endReserve, restaurant);

        System.out.println("mesas"+boardsReserveds.size());
        
        List<Integer> numBorads = boardsReserveds.stream().map(b->b.getBoard().getNumber()).toList();

        var allBoards = boardService.allBoards(restaurant);


        System.out.println(allBoards.toString());

        List<Integer> allBoard = allBoards.stream().map(r -> r.getNumber()).collect(Collectors.toList());

        System.out.println("mesas"+numBorads.size());

        if (numBorads.size() > 0) {
            ArrayList<Integer> freeBoards = new ArrayList<>();
            for (Integer b : numBorads) {
                if (!allBoard.contains(b)) {
                    freeBoards.add(b);
                }
            }
            return freeBoards;
        } else {
            return allBoard;
        }

    }

}
