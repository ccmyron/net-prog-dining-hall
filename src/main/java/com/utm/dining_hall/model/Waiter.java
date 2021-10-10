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
import java.util.concurrent.TimeUnit;

public class Waiter implements Runnable {

    public void sendOrder(Order order) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9091/receive-order";

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
        TimeUnit.SECONDS.sleep(3);
        sendOrder(generateOrder());
    }
}
