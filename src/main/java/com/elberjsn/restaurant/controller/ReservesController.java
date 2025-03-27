package com.elberjsn.restaurant.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.service.ReserveService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/my/reserve")
public class ReservesController {
    @Autowired
    ReserveService reserveService;

    @PostMapping("/active")
    public ResponseEntity<String> reservesActiveToday(HttpServletRequest request) {
        LocalDate today = LocalDate.now();
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");

        String reservesActive = String.valueOf(reserveService.findByReservesToday(today, id).stream().count());

        return ResponseEntity.ok(reservesActive);
    }

    @PostMapping("/availableBoadsByDate")
    public ResponseEntity<String> boardsAvailableByDate(@RequestParam("dateNewReserve") String dateNewReserve,
            @RequestParam("timeNewReserve") String timeNewReserve, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");

        LocalDate dataFind = LocalDate.parse(dateNewReserve);
        LocalTime timeFind = LocalTime.parse(timeNewReserve);

        var board = reserveService.reserveBetweenDate(dataFind, timeFind, id);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(board);
            return ResponseEntity.ok(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Sem Mesas Disponiveis!");
    }

    @GetMapping("/weekChart")
    public ResponseEntity<String> weekChart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");
        LocalDate week = LocalDate.now();
        List<Reserve> reserves = reserveService.reserveWeek(week, id);
        Map<String, Integer> charData = new HashMap<>() {
            {
                put(week.getDayOfWeek().toString(), 0);
                put(week.plusDays(1).getDayOfWeek().toString(), 0);
                put(week.plusDays(2).getDayOfWeek().toString(), 0);
                put(week.plusDays(3).getDayOfWeek().toString(), 0);
                put(week.plusDays(4).getDayOfWeek().toString(), 0);
                put(week.plusDays(5).getDayOfWeek().toString(), 0);
                put(week.plusDays(6).getDayOfWeek().toString(), 0);
            }
        };
        for (Reserve reserve : reserves) {
            if (reserve.getDtReserve().equals(week)) {
                charData.replace(week.getDayOfWeek().toString(), charData.get(week.getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(1))) {
                charData.replace(week.plusDays(1).getDayOfWeek().toString(),
                        charData.get(week.plusDays(1).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(2))) {
                charData.replace(week.plusDays(2).getDayOfWeek().toString(),
                        charData.get(week.plusDays(2).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(3))) {
                charData.replace(week.plusDays(3).getDayOfWeek().toString(),
                        charData.get(week.plusDays(3).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(4))) {
                charData.replace(week.plusDays(4).getDayOfWeek().toString(),
                        charData.get(week.plusDays(4).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(5))) {
                charData.replace(week.plusDays(5).getDayOfWeek().toString(),
                        charData.get(week.plusDays(5).getDayOfWeek().toString()) + 1);
            } else if (reserve.getDtReserve().equals(week.plusDays(6))) {
                charData.replace(week.plusDays(6).getDayOfWeek().toString(),
                        charData.get(week.plusDays(6).getDayOfWeek().toString()) + 1);
            }

        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(charData);
            return ResponseEntity.ok(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("{}");
    }

}
