package com.elberjsn.restaurant.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elberjsn.restaurant.models.Board;
import com.elberjsn.restaurant.service.BoardService;
import com.elberjsn.restaurant.service.RestaurantService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/my/board")
public class BoardsController {

    @Autowired
    BoardService boardService;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/")
    public String getMethodName(Model model) {
        return "infos/boards";
    }
    
    @PostMapping("/disposables")
    public ResponseEntity<Integer> boardsActiveToday(HttpServletRequest request) {
        LocalDate today = LocalDate.now();
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");
        
        
        int boardDisposable = (int) boardService.boardsDisposable(id, today).stream().count();
        System.out.println("ok");
        return ResponseEntity.ok(boardDisposable);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBoard(@RequestParam("numNewBoard") String numNewBoard,
            @RequestParam("qtdNewBoard") String qtdNewBoard, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");
        Board newBoard = new Board();
        newBoard.setCapacity(Integer.valueOf(qtdNewBoard));
        newBoard.setNumber(Integer.valueOf(numNewBoard));
        newBoard.setRestaurant( restaurantService.restaurantById(id));

        var b = boardService.saveBoard(newBoard);
            if (b.getId()!=null) {
                return ResponseEntity.ok("Mesa: "+numNewBoard+" Com "+qtdNewBoard+" Lugares esta Cadastrada!");
            }else{
                return ResponseEntity.badRequest().body("Não Foi Possivel Cadastrar a mesa!");
            }
    }

}
