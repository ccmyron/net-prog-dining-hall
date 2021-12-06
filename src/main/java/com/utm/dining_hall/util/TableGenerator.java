package com.utm.dining_hall.util;

import com.utm.dining_hall.model.Table;

import java.util.LinkedList;
import java.util.List;

public class TableGenerator {
    public static List<Table> generateTables(int nrOfTables) {
        List<Table> tables = new LinkedList<>();

        for (int i = 0; i < nrOfTables; i++) {
            tables.add(new Table());
        }

        return tables;
    }
}
