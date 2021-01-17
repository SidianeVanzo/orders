package com.example.orders.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrdersServiceTest {

    @Autowired
    private OrdersService ordersService;

    @Test
    public void checkPeriod_defaultInterval_success() throws Exception {
        String[] dates = new String[]{"2017-01-01 00:00:00", "2021-01-16 00:00:00"};
        assertNotNull(ordersService.checkPeriod(dates));
    }

    @Test
    public void checkPeriod_success() throws Exception {
        String[] dates = new String[]{"2017-01-01 00:00:00", "2021-01-16 00:00:00", "<12, >12"};
        LinkedHashMap<String, String> response = ordersService.checkPeriod(dates);
        assertNotNull(response);
        assertEquals("4 orders", response.get("<12 months"));
        assertEquals("1 orders", response.get(">12 months"));
    }

    @Test
    public void checkPeriod_wrongInterval_failed() {
        String[] dates = new String[]{"2017-01-01 00:00:00"};
        Exception exception = assertThrows(Exception.class, () -> {
            ordersService.checkPeriod(dates);
        });
        assertEquals("You must to inform the start date, the end date and the intervals", exception.getMessage());
    }

    @Test
    public void checkPeriod_failed() {
        String[] dates = new String[]{"2017-01-01 00:00:00", "2021-01-16 00:00:00", "12, 10"};
        Exception exception = assertThrows(Exception.class, () -> {
            ordersService.checkPeriod(dates);
        });
        assertEquals("The interval format is wrong. Please review.", exception.getMessage());
    }
}
