package com.utm.dining_hall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utm.dining_hall.model.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class WaiterController {

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String getOrder() throws JsonProcessingException {

        Random rand = new Random();
        List<Integer> randomItems = new ArrayList<>();
        randomItems.add(1);
        randomItems.add(3);

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new Order(
                rand.nextInt(100),
                randomItems,
                3,
                35
        ));
    }
}
