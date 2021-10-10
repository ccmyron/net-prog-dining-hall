package com.utm.dining_hall.service;

import com.utm.dining_hall.model.Food;
import com.utm.dining_hall.model.Waiter;
import com.utm.dining_hall.util.Menu;
import com.utm.dining_hall.model.Table;
import com.utm.dining_hall.util.TableGenerator;
import com.utm.dining_hall.util.WaiterGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DiningHallService {

    private static DiningHallService singleInstance;
    @Getter private final List<Food> menu = Menu.fillMenu();
    @Getter private final List<Table> tables = TableGenerator.generateTables(10);
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

        for (Table table : tables) {
            Thread thread = new Thread(table);
            thread.start();
        }
    }
}
