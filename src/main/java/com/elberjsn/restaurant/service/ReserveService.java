package com.elberjsn.restaurant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.repository.ReserveRepository;

@Service
public class ReserveService {

    @Autowired
    ReserveRepository reserveRepository;

    RestaurantService restaurantService = new RestaurantService();
    BoardService boardService = new BoardService();

    
    
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

    public Optional<Reserve> findReserveByDay(LocalDate dt){
        
        return reserveRepository.findByDtReserveStartBetween(dt.atTime(LocalTime.MIN), dt.atTime(LocalTime.MAX));
    }
    
    

}
