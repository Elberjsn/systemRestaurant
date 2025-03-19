package com.elberjsn.restaurant.controller;

import java.io.IOException;

import com.elberjsn.restaurant.security.JwtUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/my/*")
public class FilterJwt implements Filter {
 private final JwtUtil jwtUtil;

    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            System.out.println("hello");
                if (true) {
                    return;
                }
    }

}
