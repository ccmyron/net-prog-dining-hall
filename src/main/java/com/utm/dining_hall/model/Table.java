package com.utm.dining_hall.model;

import com.utm.dining_hall.service.DiningHallService;
import com.utm.dining_hall.util.Menu;
import com.utm.dining_hall.util.TableStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
public class Table implements Runnable {

    Logger log = LogManager.getLogger(Table.class);

    @Getter
    @Setter
    private UUID tableId;
    @Getter
    @Setter
    private TableStatus tableStatus;
    @Getter
    @Setter
    private Order currentOrder;


    public Table() {
        tableStatus = TableStatus.OCCUPIED;
    }

    public static Order generateOrder() {

        Random rand = new Random();
        List<Integer> items = new ArrayList<>();

        /* Generate food for the order */
        int itemsSize = rand.nextInt(3) + 1;
        for (int i = 0; i < itemsSize; i++) {
            items.add(DiningHallService.getInstance().getMenu().get(rand.nextInt(9)).getId());
        }

        return new Order(
                UUID.randomUUID(),
                items,
                rand.nextInt(5) + 1,
                (int) (Menu.findMaxWaitTime(items) * 1.3),
                false
        );
    }

    public int rateOrder(long time) {
        long timeInSeconds = time / 1000;
        long maxWait = currentOrder.getMaxWait();

        if (timeInSeconds < maxWait) {
            return 5;
        } else if (timeInSeconds < maxWait * 1.1 && timeInSeconds > maxWait) {
            return 4;
        } else if (timeInSeconds < maxWait * 1.2 && timeInSeconds > maxWait * 1.1) {
            return 3;
        } else if (timeInSeconds < maxWait * 1.3 && timeInSeconds > maxWait * 1.2) {
            return 2;
        } else if (timeInSeconds < maxWait * 1.4 && timeInSeconds > maxWait * 1.3) {
            return 1;
        } else {
            return 0;
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        long orderCreationTime = 0;
        while (true) {
            if (this.tableStatus == TableStatus.OCCUPIED) {
                /* Wait 5-15 seconds to make an order */
                TimeUnit.SECONDS.sleep(new Random().nextInt(10) + 5);
                currentOrder = generateOrder();
                orderCreationTime = System.currentTimeMillis();

                /* Announce that the order is made */
                tableStatus = TableStatus.WAITING_TO_MAKE_ORDER;
                log.info("Order pending");
            }

            if (this.tableStatus == TableStatus.RECEIVED_ORDER) {
                log.info("Order took {} seconds to come back, rating: {}",
                        (System.currentTimeMillis() - orderCreationTime) / 1000,
                        rateOrder(System.currentTimeMillis() - orderCreationTime));

                this.tableStatus = TableStatus.OCCUPIED;
            }
        }
    }
}
