package com.elberjsn.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elberjsn.restaurant.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/my/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/online")
    public ResponseEntity<Long> employeeOnline(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        Long id = (Long) session.getAttribute("utilizadorId") ;
        var qtd = employeeService.EmployeeOnline(id).stream().count();
        
        return ResponseEntity.ok(qtd);
    }
    
}
