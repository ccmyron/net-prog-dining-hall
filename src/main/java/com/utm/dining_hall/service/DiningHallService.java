package com.utm.dining_hall.service;

import com.utm.dining_hall.model.Food;
import com.utm.dining_hall.model.Waiter;
import com.utm.dining_hall.util.Menu;
import com.utm.dining_hall.model.Table;
import com.utm.dining_hall.util.WaiterGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class DiningHallService {

    private static DiningHallService singleInstance;
    @Getter private final List<Food> menu = Menu.fillMenu();
    private List<Table> tables = new ArrayList<>();
    @Getter @Setter private List<Waiter> waiters = WaiterGenerator.generateWaiters(5);

    private DiningHallService() {}

    public static DiningHallService getInstance()
    {
        if (singleInstance == null)
            singleInstance = new DiningHallService();

        return singleInstance;
    }

    public void openDiningHall() {
        for (Waiter waiter : waiters) {
            Thread thread = new Thread(waiter);
            thread.start();
        }
    }
}
