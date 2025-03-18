package com.elberjsn.restaurant.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elberjsn.restaurant.service.BoardService;
import com.elberjsn.restaurant.service.ReserveService;
import com.elberjsn.restaurant.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

@Controller
@RestController
public class utilsController {

    @Autowired
    ReserveService reserveService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    BoardService boardService;

    @GetMapping("my/reserves/boards")
    public ResponseEntity<List<Integer>> data(@RequestParam String dt,@RequestParam String hours, HttpSession httpSession) {

        LocalDate lc = LocalDate.parse(dt);
        LocalTime h = LocalTime.parse(hours);

        var cnpj = httpSession.getAttribute("key");
        System.out.println(cnpj.toString());
        var id = restaurantService.restaurantByCnpjReturnLong(cnpj.toString());

        
        var bord = reserveService.findBoardbyDateForReserve(lc, id , h); 

        return ResponseEntity.status(HttpStatus.OK).body(bord);

    }

}
