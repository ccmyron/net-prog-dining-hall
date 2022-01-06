package com.utm.dining_hall.util;

import lombok.SneakyThrows;

import java.io.InputStream;

public class Properties {
    public static int TIME_UNIT;
    public static int TABLES;
    public static int WAITERS;

    @SneakyThrows
    public static void readProperties() {
        InputStream s = Properties.class.getResourceAsStream("/application.properties");

        java.util.Properties props = new java.util.Properties();
        props.load(s);

        TIME_UNIT = Integer.parseInt(props.getProperty("time_unit"));
        TABLES = Integer.parseInt(props.getProperty("tables"));
        WAITERS = Integer.parseInt(props.getProperty("waiters"));
    }
}
