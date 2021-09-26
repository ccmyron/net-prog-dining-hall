package com.utm.dining_hall.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utm.dining_hall.service.DiningHallService;
import com.utm.dining_hall.util.Menu;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Waiter implements Runnable {

    public static Order generateOrder() {

        Random rand = new Random();
        List<Integer> items = new ArrayList<>();

        int itemsSize = rand.nextInt(3) + 1;
        for (int i = 0; i < itemsSize; i++) {
            items.add(DiningHallService.getInstance().getMenu().get(rand.nextInt(9)).getId());
        }

        return new Order(
                UUID.randomUUID(),
                items,
                rand.nextInt(5) + 1,
                (int) (Menu.findMaxWaitTime(items) * 1.3)
        );
    }

    public void sendOrder(Order order) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9091/receive-order";

        try {
            URI uri = new URI(url);
            HttpEntity<Order> requestEntity = new HttpEntity<>(order, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(uri, requestEntity, String.class);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        TimeUnit.SECONDS.sleep(3);
        sendOrder(generateOrder());
    }
}
