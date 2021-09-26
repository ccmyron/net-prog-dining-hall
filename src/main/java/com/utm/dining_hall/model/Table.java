package com.utm.dining_hall.model;

import lombok.Data;

@Data
public class Table {

    private boolean isOccupied;
    private boolean isWaitingToMakeAnOrder;
    private boolean isWaitingForTheOrder;

    public Table() {
        isOccupied = true;
        isWaitingToMakeAnOrder = true;
        isWaitingForTheOrder = false;
    }

}
