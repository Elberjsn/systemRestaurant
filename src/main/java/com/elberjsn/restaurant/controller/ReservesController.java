package com.elberjsn.restaurant.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elberjsn.restaurant.service.ReserveService;

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
        
        Long id = (Long) session.getAttribute("utilizadorId") ;
        
        String reservesActive = String.valueOf(reserveService.findByReservesToday(today, id ).stream().count());

        return ResponseEntity.ok(reservesActive);
    }
    

}
