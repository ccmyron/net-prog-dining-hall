package com.utm.dining_hall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {

    @GetMapping("/table")
    public String getTableStatus() {
        return "isEmpty";
    }
}
