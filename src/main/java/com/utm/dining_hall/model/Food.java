package com.utm.dining_hall.model;

import lombok.Data;

@Data
public class Food {
    private int id;
    private String name;
    private int prepTime;
    private int complexity;
    private String cookingApparatus;
}
