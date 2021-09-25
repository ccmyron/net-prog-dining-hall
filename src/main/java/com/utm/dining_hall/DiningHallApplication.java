package com.utm.dining_hall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DiningHallApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiningHallApplication.class, args);
    }

}
