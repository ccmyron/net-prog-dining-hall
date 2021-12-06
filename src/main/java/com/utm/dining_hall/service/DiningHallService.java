package com.utm.dining_hall.service;

import com.utm.dining_hall.model.Food;
import com.utm.dining_hall.model.Order;
import com.utm.dining_hall.model.Waiter;
import com.utm.dining_hall.util.Menu;
import com.utm.dining_hall.model.Table;
import com.utm.dining_hall.util.TableGenerator;
import com.utm.dining_hall.util.TableStatus;
import com.utm.dining_hall.util.WaiterGenerator;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class DiningHallService {

    private static DiningHallService instance;
    private final List<Food> menu;
    private final List<Table> tables;
    private final List<Waiter> waiters;
    private final CopyOnWriteArrayList<Order> doneOrders = new CopyOnWriteArrayList<>();

    private DiningHallService() {
        menu = Menu.createMenu();
        tables = TableGenerator.generateTables(1);
        waiters = WaiterGenerator.generateWaiters(1);
    }

    public static DiningHallService getInstance() {
        if (instance == null) {
            instance = new DiningHallService();
        }

        return instance;
    }

    public boolean hasWaitingTables() {
        for (Table table : tables) {
            if (table.getTableStatus() == TableStatus.WAITING_TO_MAKE_ORDER) {
                return true;
            }
        }

        return false;
    }

    public synchronized boolean hasDoneOrders(){
        return !doneOrders.isEmpty();
    }

    @SneakyThrows
    public synchronized void addOrder(Order order) {
        doneOrders.add(order);
    }

    public synchronized Order peekOrder() {
        return doneOrders.get(doneOrders.size() - 1);
    }

    public synchronized void removeOrder(Order order){
        doneOrders.remove(order);
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
