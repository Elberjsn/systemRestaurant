package com.elberjsn.restaurant.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elberjsn.restaurant.controller.FilterJwt;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<FilterJwt> filterWeb() {
        FilterRegistrationBean<FilterJwt> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FilterJwt());
        registrationBean.addUrlPatterns("/my/*"); // Define os padrões de URL que o filtro irá interceptar
        return registrationBean;
    }
}
