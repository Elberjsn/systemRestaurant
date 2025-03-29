package com.elberjsn.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elberjsn.restaurant.service.RestaurantService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/my")
public class DashBoardController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/")
    public String init(Model model,HttpServletRequest request ) {
         HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");

        var hours = restaurantService.openingHours(id);
        
        if (hours == null) {
            model.addAttribute("msg", "Por favor Termine seu Cadastro!");
            return "infos/config";
        }

        return "infos/dashboard";
    }


  
    

}
