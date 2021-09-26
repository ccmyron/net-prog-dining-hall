package com.utm.dining_hall.controller;

import com.utm.dining_hall.model.Order;
import org.springframework.web.bind.annotation.*;

@RestController
public class WaiterController {

    @PostMapping(value="/receive-order", consumes="application/json", produces="application/json")
    public String getOrder(@RequestBody Order order) {

        System.out.println("Order " + order.getId() + "has returned!");

        return "Order with id " + order.getId() + " received and served";
    }
}
