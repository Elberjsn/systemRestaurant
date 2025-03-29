package com.elberjsn.restaurant.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elberjsn.restaurant.models.Client;
import com.elberjsn.restaurant.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/my/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/tel")
    public ResponseEntity<String> getMethodName(@RequestParam("tel") String tel) {

        Client c = clientService.clientByPhone(tel);

        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        if (c != null) {
            try {
                json = objectMapper.writeValueAsString(c);
               
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(json);
    }

}
