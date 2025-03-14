package com.elberjsn.restaurant.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elberjsn.restaurant.service.ReserveService;

import jakarta.servlet.http.HttpSession;

@Controller
@RestController
public class utilsController {

    @Autowired
    ReserveService reserveService;

    @GetMapping("my/reserves/boards")
    public ResponseEntity<String> data(@RequestParam String dt, HttpSession httpSession) {

        LocalDate lc = LocalDate.parse(dt);
        Long id = Long.parseLong((String) httpSession.getAttribute("key"));

        var bord = reserveService.findBoardbyDate(lc, id);

        return ResponseEntity.status(HttpStatus.OK).body(bord.toString());

    }

}
