package com.utm.dining_hall.controller;

import com.utm.dining_hall.model.Order;
import com.utm.dining_hall.service.DiningHallService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiningHallController {

    @PostMapping(value="/distribution", consumes="application/json", produces="application/json")
    public String getOrder(@RequestBody Order order) {

        Logger log = LogManager.getLogger(DiningHallController.class);

        log.info("Order {} has returned!", order.getId());
        order.setReady(true);
        DiningHallService.getInstance().addOrder(order);

        return "Order with id " + order.getId() + " received and served";
    }
}
