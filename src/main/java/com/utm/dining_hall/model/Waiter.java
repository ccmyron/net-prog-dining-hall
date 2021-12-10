package com.utm.dining_hall.model;

import com.utm.dining_hall.service.DiningHallService;
import com.utm.dining_hall.util.TableStatus;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Waiter implements Runnable {

    Logger log = LogManager.getLogger(Waiter.class);
    Optional<Order> fetchedOrder = Optional.empty();

    public void sendOrderToKitchen(Order order) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9091/order";

        try {
            URI uri = new URI(url);
            HttpEntity<Order> requestEntity = new HttpEntity<>(order, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(uri, requestEntity, String.class);
            log.info(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        DiningHallService context = DiningHallService.getInstance();
        while (true) {
            List<Table> tableList = context.getTables();
            // Check if an order has returned and give it to the corresponding table
            if (context.hasDoneOrders()) {
                synchronized (tableList) {
                    for (Table table : tableList) {
                        if (context
                                .peekOrder()
                                .getId()
                                .equals(table.getCurrentOrder().getId())
                        ) {
                            table.setTableStatus(TableStatus.RECEIVED_ORDER);
                            table.getCurrentOrder().setReady(true);
                            log.info("Received an order and sending it to the table");
                            context.removeOrder(table.getCurrentOrder());
                            break;
                        }
                    }
                }
            }
            // Check if there are tables waiting to make an order, if present - fetch and send it to the kitchen
            if (context.hasWaitingTables()) {
                synchronized (tableList) {
                    for (Table table : tableList) {
                        if (table.getTableStatus().equals(TableStatus.WAITING_TO_MAKE_ORDER)) {
                            log.info("Fetching the order");
                            TimeUnit.SECONDS.sleep(new Random().nextInt(2) + 2);
                            fetchedOrder = Optional.ofNullable(table.getCurrentOrder());
                            fetchedOrder.ifPresent(order -> {
                                log.info("Fetched an order from a table");
                                sendOrderToKitchen(order);
                            });
                            table.setTableStatus(TableStatus.WAITING_FOR_ORDER);
                            break;
                        }
                    }
                }
            }

            TimeUnit.SECONDS.sleep(5);
        }
    }
}
