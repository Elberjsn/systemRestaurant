package com.elberjsn.restaurant.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elberjsn.restaurant.models.Reserve;
import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.service.ControlService;
import com.elberjsn.restaurant.service.ReserveService;
import com.elberjsn.restaurant.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/my")
public class DashBoardController {

    Restaurant restaurant = new Restaurant();
    List<?> clientsToday = new ArrayList<>();
    int boardDisposable = 0; // falta
    double balanceToday = 0;

    int reservesToday = 0;
    int clientToday = 0;

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    ReserveService reserveService;
    @Autowired
    ControlService controlService;

    @GetMapping("/")
    public String getMethodName(HttpSession httpSession, Model model) {
        String cnpj = (String) httpSession.getAttribute("key");
        initDash(cnpj);

        model.addAttribute("restaurant", this.restaurant);
        model.addAttribute("reservesToday", this.reservesToday);
        model.addAttribute("clientToday", this.clientToday);
        model.addAttribute("clientsToday", this.clientsToday);
        model.addAttribute("balanceToday", this.balanceToday);

        model.addAttribute("key", cnpj);
    
        return "infos/dashboard";
    }

    public void initDash(String cnpj) {
        this.restaurant = restaurantService.restaurantByCnpj(cnpj);
        Optional<Reserve> listReserve = reserveService.findReserveByDay(LocalDate.now());

        this.reservesToday = (int) listReserve.stream().count();

        this.clientToday = listReserve.stream().mapToInt(Reserve::getQuantity).sum();

        this.balanceToday = controlService.balanceToday(LocalDate.now());

        this.clientsToday = listReserve
                .map(reserve -> Arrays.asList(reserve.getClient().getName(), reserve.getQuantity()))
                .orElse(Arrays.asList("0", 0));

    }
}
