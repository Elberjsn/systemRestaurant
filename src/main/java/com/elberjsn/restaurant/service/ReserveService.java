package com.elberjsn.restaurant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.repository.ReserveRepository;


public class ReserveService {

    @Autowired
    ReserveRepository reserveRepository;

    RestaurantService restaurantService = new RestaurantService();
    BoardService boardService = new BoardService();

    
    public List<Long> freeBoards(Reserve reserve, Long restaurant){
        //mesas no restaurantes
        List<Long> allBoards = boardService.allBoards(restaurant).stream().map(Board::getId).collect(Collectors.toList());


        List<Long> reservedBoard = reserveRepository.findBoardIdsByRestaurantIdAndBetween(restaurant, reserve.getDtReserveStart(), reserve.getDtReserveEnd());

        return allBoards.stream().filter(board -> reservedBoard.contains(board)).collect(Collectors.toList());
    } 

    public Boolean verificHours(Reserve reserve,Long restaurant){

        var hours = restaurantService.openingHours(restaurant);
        

        return hours.contains(reserve.getDtReserveStart().toLocalTime());

    }   

    @Transactional
    public Reserve save(Reserve reserve,Long restaurant){
        if (verificHours(reserve, restaurant)) {
           return reserveRepository.save(reserve);
        }else{
            return null;
        }
    }
    
    

}
