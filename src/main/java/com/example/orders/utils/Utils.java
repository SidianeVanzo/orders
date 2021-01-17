package com.example.orders.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class Utils {

    public LocalDateTime getOldDate(String months) {
        return LocalDateTime.now().minusMonths(Integer.valueOf(months));
    }

    public LocalDateTime convertStringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
