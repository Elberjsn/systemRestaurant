package com.elberjsn.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("page", "index");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("rest", new Restaurant());

        return "login";
    }

    @GetMapping("/cadas")
    public String cadastro(Model model) {
        model.addAttribute("rest", new Restaurant());

        return "cadas";
    }

    @PostMapping("/restaurant/login")
    public String loginRestaurant(@ModelAttribute Restaurant rest, RedirectAttributes attributes,HttpSession httpSession) {
        String r = restaurantService.loginRestaurantReturnCNPJ(rest);
        System.out.println(r);
        if (r != null) {
            httpSession.setAttribute("key", r.toString());
            return "redirect:/my/";
        } else {
            attributes.addFlashAttribute("msg", "Informações de Login invalida!");
            return "redirect:/login";
        }

    }

    @PostMapping("/restaurant/save")
    public String postMethodNames(@ModelAttribute Restaurant rest,RedirectAttributes attributes) {
        var r = restaurantService.save(rest);
        if (r.getId() != null) {
            System.out.println(r.toString());
            attributes.addFlashAttribute("msg", "Cadastro Realizado com sucesso\nPor favor entre com suas credencias!");
            return "redirect:/login";
        }else{
            attributes.addFlashAttribute("msg", "Cadastro não Realizado\nRevize suas informações!");
            return "redirect:/cadas";
        }

        

    }
    @GetMapping("/error")
    public String menu(Model model) {

        return "/error";
    }

}
