package com.elberjsn.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elberjsn.restaurant.service.ControlService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/my/orders")
public class OrdersController {

    @Autowired
    ControlService controlService;

    @PostMapping("/status")
    public String postMethodName(@RequestBody String entity) {
        
        
        return entity;
    }
     @GetMapping("/")
    public String getMethodName(Model model) {
        return "infos/menu";
    }



    @PostMapping("/open/")
    public ResponseEntity<String> initOrders(HttpServletRequest request,@RequestParam("reserveToday") String idReserve) {
        System.out.println(idReserve);
        Long idR = Long.valueOf(idReserve) ;

        var c = controlService.openControl(idR);
        if (c.getId() != null) {
            return ResponseEntity.ok( "Comanda "+ c.getNumber() +" Aberta!" );
        }
        return ResponseEntity.badRequest().body("Não Foi Possivel Agora!");
        
    }
    
    
}
