package com.elberjsn.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.security.JwtUtil;
import com.elberjsn.restaurant.service.BoardService;
import com.elberjsn.restaurant.service.RestaurantService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    BoardService boardService;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("page", "index");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("rest", new Restaurant());

        return "login";
    }

    @GetMapping("/exit")
    public String exit(Model model, HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (int i = 0; i < cookies.length; i++)
            {
                cookies[i].setMaxAge(-1); // se -1 nao funcionar tente 0 nao lembro bem essa parte
                response.addCookie(cookies[i]);
            }
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/cadas")
    public String cadastro(Model model) {
        model.addAttribute("rest", new Restaurant());

        return "cadas";
    }

    @PostMapping("/restaurant/login")
    public String loginRestaurant(@ModelAttribute Restaurant rest, RedirectAttributes attributes,
            HttpServletResponse response, HttpServletRequest request) {
        Restaurant r = restaurantService.loginRestaurant(rest);

        if (r != null) {
            String token = JwtUtil.gerarToken(r.getCnpj());
            if (token != null) {

                Cookie cookie = new Cookie("Authorization", token);
                cookie.setMaxAge(1000 * 60 * 360);

                HttpSession session = request.getSession();
                session.setAttribute("utilizadorId", r.getId());

                return "redirect:/my/";
            }

        } else {
            attributes.addFlashAttribute("msg", "Informações de Login invalida!");

        }
        return "redirect:/login";

    }

    @PostMapping("/restaurant/save")
    public String newRestauran(@ModelAttribute Restaurant rest, RedirectAttributes attributes) {
        var r = restaurantService.save(rest);
        if (r.getId() != null) {
            System.out.println(r.toString());
            attributes.addFlashAttribute("msg", "Cadastro Realizado com sucesso\nPor favor entre com suas credencias!");
            return "redirect:/login";
        } else {
            attributes.addFlashAttribute("msg", "Cadastro não Realizado\nRevize suas informações!");
            return "redirect:/cadas";
        }

    }

    @PostMapping("/restaurant/edit")
    public String editRestaurant(@ModelAttribute Restaurant rest, RedirectAttributes attributes) {
        var r = restaurantService.editRestaurantAll(rest);
        if (r.getId() != null) {
            System.out.println(r.toString());
            attributes.addFlashAttribute("msg", "Cadastro Realizado com sucesso\nPor favor entre com suas credencias!");
            return "redirect:/login";
        } else {
            attributes.addFlashAttribute("msg", "Cadastro não Realizado\nRevize suas informações!");
            return "redirect:/cadas";
        }

    }

    @GetMapping("/error")
    public String menu(Model model) {
        return "error";
    }

}
