package com.example.orders;

import com.example.orders.services.OrdersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OrdersApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = SpringApplication.run(OrdersApplication.class, args);
		OrdersService ordersService = applicationContext.getBean(OrdersService.class);
		ordersService.checkPeriod(args);
	}
}