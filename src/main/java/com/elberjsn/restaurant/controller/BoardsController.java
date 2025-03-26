package com.elberjsn.restaurant.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elberjsn.restaurant.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/my/board")
public class BoardsController {

    @Autowired
    BoardService boardService ;

    @PostMapping("/disposables")
   public ResponseEntity<Integer> boardsActiveToday(HttpServletRequest request) {
        LocalDate today = LocalDate.now();
        HttpSession session = request.getSession(false);
        
        Long id = (Long) session.getAttribute("utilizadorId") ;
        
        int boardDisposable = (int) boardService.boardsDisposable(id,today).stream().count();

        return ResponseEntity.ok(boardDisposable);
    }
    
}
