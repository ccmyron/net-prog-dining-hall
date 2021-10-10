package com.utm.dining_hall.controller;

import com.utm.dining_hall.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiningHallController {

    @PostMapping(value="/receive-order", consumes="application/json", produces="application/json")
    public String getOrder(@RequestBody Order order) {

        Logger log = LogManager.getLogger(DiningHallController.class);

        log.info("Order {} has returned!", order.getId());

        return "Order with id " + order.getId() + " received and served";
    }
}
