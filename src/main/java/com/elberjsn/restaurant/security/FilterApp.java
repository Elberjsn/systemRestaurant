package com.elberjsn.restaurant.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class FilterApp implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (verificaCookies(httpRequest, httpResponse) == true && verificaSessao(httpRequest) == true) {
            chain.doFilter(request, response);
        }
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");

        
    }

    private Boolean verificaCookies(HttpServletRequest httpRequest,HttpServletResponse httpResponse) {
        Optional<Cookie> tokenCookie = obterCookie(httpRequest, "Authorization");
        if (tokenCookie.isPresent()) {
            String token = tokenCookie.get().getValue();
            if (JwtUtil.validarToken(token) && JwtUtil.isTokenExpirado(token)) {
                System.out.println("true");
               return true;
            }
            return false;

        } else {
            return false;
        }
    }

    private Boolean verificaSessao(HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession(false); // Não cria uma sessão se não existir

        if (session != null && session.getAttribute("token") != null) {
            System.out.println("true");

            return true;

        } else {
            return false;
        }
        
    }

    private Optional<Cookie> obterCookie(HttpServletRequest request, String nome) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> nome.equals(cookie.getName()))
                    .findFirst();
        }
        return Optional.empty();
    }

}
