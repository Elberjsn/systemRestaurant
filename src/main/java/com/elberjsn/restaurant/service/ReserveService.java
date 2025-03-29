package com.elberjsn.restaurant.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    public Boolean verificHours(Reserve reserve) {

        List<LocalTime> hours = restaurantService.openingHours(reserve.getRestaurant().getId());

        return !hours.contains(reserve.getHoursStart());

    }

    @Transactional
    public Reserve save(Reserve reserve) {
        if (verificHours(reserve)) {
            return reserveRepository.save(reserve);
        } else {
            return null;
        }
    }

    public Reserve findById(Long id) {
        return reserveRepository.findById(id).orElse(null);
    }

    @Cacheable(value="ReservesToday",key = "{#id, #today}")
    public List<Reserve> findByReservesToday(LocalDate today, Long id) {
        System.out.println("cache");
        return reserveRepository.findByDtReserveAndRestaurantId(today, id);
    }

    public List<Reserve> reserveWeek(LocalDate data, Long id) {
        LocalDate start = data;
        LocalDate end = start.plusDays(7);

        return reserveRepository.findByDtReserveBetweenAndRestaurantId(start, end, id);
    }

    public Map<Integer, Integer> reserveBetweenDate(LocalDate data, LocalTime hour, Long id) {
        hour = hour.minusMinutes(30);
        LocalTime end = hour.plusHours(2).plusMinutes(20);

        List<Reserve> reserves = findByReservesToday(data, id);

        // List<Integer> allBoards =listBoard.stream().map(b ->
        // b.getNumber()).collect(Collectors.toList());

        var listBoard = boardRepository.findByRestaurantId(id);
        HashMap<Integer, Integer> boardsAll = (HashMap<Integer, Integer>) listBoard.stream()
                .collect((Collectors.toMap(Board::getNumber, Board::getCapacity)));

        for (Reserve reserve : reserves) {

            if (reserve.getHoursStart().isAfter(hour) && reserve.getHoursEnd().isBefore(end)) {
                boardsAll.remove(reserve.getBoard().getNumber());
            }

        }

        return boardsAll;

    }

    public List<String> boardsReservesToday(LocalDate data, Long id) {
        List<Reserve> reserves = findByReservesToday(data, id).stream().filter(item -> item.getControl() != null)
        .collect(Collectors.toList());;
        List<String> obj = reserves.stream().map((reserve -> reserve.getId() + ","
                + reserve.getBoard().getNumber() + "," + reserve.getClient().getName()+","+reserve.getHoursStart())).collect(Collectors.toList());

       
        return obj;
    }
    public void initControl(Reserve reserve){
        save(reserve);
    }

}
