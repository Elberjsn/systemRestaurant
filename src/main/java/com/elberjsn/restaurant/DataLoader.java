package com.elberjsn.restaurant;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.repository.RestaurantRepository;

@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private Environment environment;
    
    @Autowired
    private RestaurantRepository repository;
    @Override
    public void run(String... args) throws Exception {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
           Restaurant r = new Restaurant();
           r.setName("teste");
           r.setCnpj("12345");
           r.setPassword("12345");

           repository.save(r);
        } else {
            System.out.println("Active profiles: " + Arrays.toString(activeProfiles));
        }
    }

}
