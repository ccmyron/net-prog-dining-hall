package com.utm.dining_hall.model;

import com.utm.dining_hall.service.DiningHallService;
import com.utm.dining_hall.util.Menu;
import com.utm.dining_hall.util.TableStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
public class Table implements Runnable {

    @Getter @Setter private TableStatus tableStatus;

    public Table() {
        tableStatus = TableStatus.OCCUPIED;
    }

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

    @SneakyThrows
    @Override
    public void run() {
        /* Wait 5-15 seconds to make an order */
        TimeUnit.SECONDS.sleep(new Random().nextInt(10) + 5);
        sendOrder(generateOrder());
    }
}
