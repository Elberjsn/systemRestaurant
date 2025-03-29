package com.elberjsn.restaurant.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.service.RestaurantService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/my/config")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/")
    public String getMethodName(Model model) {
        return "infos/config";
    }

     @PostMapping("/edit")
    public String postMethodName(@RequestParam("open") String open,
            @RequestParam("closed") String closed,
            @RequestParam("tel") String tel,
            @RequestParam("address") String address, @RequestParam("site") String site, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Long id = (Long) session.getAttribute("utilizadorId");

        Restaurant r = restaurantService.restaurantById(id);
        r.setAddress(address);
        r.setOpening(LocalTime.parse(open));
        r.setClosed(LocalTime.parse(closed));
        r.setPhone(tel);
        r.setSite(site);

        restaurantService.save(r);

        return "redirect:/my/";
    }
    
}
