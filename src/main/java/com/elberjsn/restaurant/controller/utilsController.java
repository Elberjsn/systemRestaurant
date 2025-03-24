package com.elberjsn.restaurant.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elberjsn.restaurant.security.JwtUtil;
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
    public String data(@RequestParam String dt,@RequestParam String hours, HttpSession httpSession,Model model) {

        LocalDate lc = LocalDate.parse(dt);
        LocalTime h = LocalTime.parse(hours);

        var cnpj = JwtUtil.decoderToken((String) httpSession.getAttribute("token"));
        System.out.println(cnpj);
        var id = restaurantService.restaurantByCnpjReturnLong(cnpj.toString());

        
        var board = reserveService.findBoardbyDateForReserve(lc, id , h);
        model.addAttribute("boards", board);
        String boads = null;
            for (Integer integer : board) {
                boads += "<button value="+integer+">"+integer+"</button>";
            }
        return boads;

    }

    
    

    public String redirectPages(Model model,String link){
        return link;
    }

}
