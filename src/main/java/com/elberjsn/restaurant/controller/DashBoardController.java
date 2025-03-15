package com.elberjsn.restaurant.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    int boardsFreeToday = 0;

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    ReserveService reserveService;

    @Autowired
    ControlService controlService;

    @GetMapping("/")
    public String getMethodName(HttpSession httpSession, Model model) {
        String cnpj = (String) httpSession.getAttribute("key");
        DateTimeFormatter formData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (cnpj == null) {
            return "redirect:/login";
        }

        initDash(cnpj);
        httpSession.setAttribute("key", this.restaurant.getId());

        model.addAttribute("restaurant", this.restaurant);
        model.addAttribute("reservesToday", this.reservesToday);
        model.addAttribute("clientToday", this.clientToday);
        model.addAttribute("clientsToday", this.clientsToday);
        model.addAttribute("balanceToday", this.balanceToday);
        model.addAttribute("boardsFreeToday", this.boardsFreeToday);
        model.addAttribute("today", LocalDate.now().format(formData));

        

        model.addAttribute("key", cnpj);

        return "infos/dashboard";
    }

    @GetMapping("/reserves")
    public String reserves(Model model) {
        model.addAttribute("restaurant", this.restaurant);

        return "infos/reserves";
    }

    @GetMapping("/boards")
    public String boards(Model model) {
        model.addAttribute("restaurant", this.restaurant);

        return "infos/boards";
    }

    @GetMapping("/employees")
    public String emplooyes(Model model) {
        model.addAttribute("restaurant", this.restaurant);

        return "infos/reserves";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("restaurant", this.restaurant);

        return "infos/menu";
    }

    @GetMapping("/config")
    public String config(Model model) {
        model.addAttribute("restaurant", this.restaurant);

        return "infos/config";
    }

    private void initDash(String cnpj) {
        this.restaurant = restaurantService.restaurantByCnpj(cnpj);

        Set<Reserve> listReserve = reserveService.findReserveByDay(LocalDate.now());

        this.reservesToday = (int) listReserve.stream().count();

        this.clientToday = listReserve.stream().mapToInt(Reserve::getQuantity).sum();

        this.balanceToday = controlService.balanceToday(LocalDate.now());

        var r= reserveService.findBoardbyDate(LocalDate.now(), this.restaurant.getId());
        this.boardsFreeToday = r.size();
        System.out.println(r);

        this.clientsToday = listReserve.stream()
                .map(reserve -> {
                    if (reserve.getClient() != null && reserve.getClient().getName() != null) {
                        return Arrays.asList(reserve.getClient().getName(), reserve.getQuantity());
                    } else {
                        return Arrays.asList("Cliente desconhecido", reserve.getQuantity());
                    }
                })
                .collect(Collectors.toList());

    }
}
