package com.demo.order;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

	private final OrderRepo orderRepo;

	public OrderService(OrderRepo orderRepo) {
		super();
		this.orderRepo = orderRepo;
	}
	
	public List<Order> findAllOrder(){
		return orderRepo.findAll();
	}
	
	public Order save(Order order) {
		order.setId(generateId());
		return orderRepo.save(order);
	}
	
	public String generateId() {
		return RandomStringUtils.randomAlphabetic(6).toUpperCase();
	}	
	
	public void removeAll() {
		orderRepo.deleteAll();
	}
}
