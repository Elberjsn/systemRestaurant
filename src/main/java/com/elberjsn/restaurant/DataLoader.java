package com.elberjsn.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private Environment environment;


    @Override
    public void run(String... args) throws Exception {
        var r= environment.getActiveProfiles()[0].equals("dev");
        System.out.println(r);

    }

}
