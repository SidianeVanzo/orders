package com.example.orders.services;

import com.example.orders.entities.Item;
import com.example.orders.entities.Orders;
import com.example.orders.repositories.OrdersRepository;
import com.example.orders.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersService {

	private static String DEFAULT_INTERVAL = "1-3, 4-6, 7-12, >12";
	private OrdersRepository ordersRepository;
	private Utils utils;

	public LinkedHashMap<String, String> checkPeriod(String[] args) throws Exception {
		List<String> argsList = checkPeriodSize(args);
		LinkedHashMap<String, String> result = null;

		List<Orders> orders = getOrdersList(argsList);
		if (!orders.isEmpty()) {
			result = builderResult(argsList.get(2), orders);
		}

		System.out.println(result);
		return result;
	}

	private LinkedHashMap<String, String> builderResult(String intervalsList, List<Orders> orders) throws Exception {
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		List<String> intervals = Arrays.asList(intervalsList.replaceAll("\\s","").split(","));
		for (String interval : intervals) {
			Long totalOrders;
			if (interval.contains("-")){
				totalOrders = getCountByAfterAndBefore(orders, interval);
			} else if (interval.contains(">")) {
				totalOrders = getCountByAfter(orders, utils.getOldDate(interval.substring(1)));
			} else if (interval.contains("<")) {
				totalOrders = getCountByBefore(orders, utils.getOldDate(interval.substring(1)));
			} else {
				throw new Exception("The interval format is wrong. Please review.");
			}

			result.put(interval.concat(" months"), totalOrders + " orders");
		}
		return result;
	}

	private Long getCountByAfterAndBefore (List<Orders> orders, String interval) {
		List<String> intervalRangeList = Arrays.asList(interval.split("-"));
		LocalDateTime startRange = utils.getOldDate(intervalRangeList.get(0));
		LocalDateTime endRange = utils.getOldDate(intervalRangeList.get(1));
		return orders.parallelStream().filter(order -> checkProductDateBetween(order.getItems(), startRange, endRange)).count();
	}

	private boolean checkProductDateBetween(List<Item> items, LocalDateTime startRange, LocalDateTime endRange) {
		return items.parallelStream().anyMatch(item -> item.getProduct().getCreationDate().isBefore(startRange) && item.getProduct().getCreationDate().isAfter(endRange));
	}

	private Long getCountByAfter (List<Orders> orders, LocalDateTime interval) {
		return orders.parallelStream()
				     .filter(order -> order.getItems().parallelStream().anyMatch(item -> interval.isAfter(item.getProduct().getCreationDate())))
				     .count();
	}

	private Long getCountByBefore (List<Orders> orders, LocalDateTime interval) {
		return orders.parallelStream()
				     .filter(order -> order.getItems().stream().anyMatch(item -> interval.isBefore(item.getProduct().getCreationDate())))
				     .count();
	}

	private List<Orders> getOrdersList(List<String> args) {
		LocalDateTime startTime = utils.convertStringToDate(args.get(0));
		LocalDateTime endTime = utils.convertStringToDate(args.get(1));
		List<Orders> orders = ordersRepository.findOrders(startTime, endTime);
		return orders;
	}

	private List<String> checkPeriodSize(String[] args) throws Exception {
		List<String> period = Arrays.stream(args).collect(Collectors.toList());
		if (period.size() < 2 || period.size() > 3){
			throw new Exception("You must to inform the start date, the end date and the intervals");
		} else if (period.size() == 2){
			period.add(DEFAULT_INTERVAL);
		}
		return period;
	}
}