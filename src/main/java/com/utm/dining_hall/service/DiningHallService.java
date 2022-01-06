package com.utm.dining_hall.service;

import com.utm.dining_hall.model.Food;
import com.utm.dining_hall.model.Order;
import com.utm.dining_hall.model.Waiter;
import com.utm.dining_hall.util.*;
import com.utm.dining_hall.model.Table;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
        tables = TableGenerator.generateTables(Properties.TABLES);
        waiters = WaiterGenerator.generateWaiters(Properties.WAITERS);
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

    public void sendOrderToKitchen(Order order) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9091/order";

        try {
            URI uri = new URI(url);
            HttpEntity<Order> requestEntity = new HttpEntity<>(order, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(uri, requestEntity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
