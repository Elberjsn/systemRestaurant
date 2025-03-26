package com.elberjsn.restaurant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public List<Reserve> findByReservesToday(LocalDate today,Long id){
        return reserveRepository.findByDtReserveAndRestaurantId(today,id);
    }



}
