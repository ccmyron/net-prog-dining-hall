package com.utm.dining_hall;

import com.utm.dining_hall.service.DiningHallService;
import com.utm.dining_hall.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiningHallApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiningHallApplication.class, args);
        Properties.readProperties();
        DiningHallService diningHallService = DiningHallService.getInstance();
        diningHallService.openDiningHall();
    }
}
