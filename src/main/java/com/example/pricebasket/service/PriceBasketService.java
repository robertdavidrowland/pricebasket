package com.example.pricebasket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pricebasket.model.Basket;
import com.example.pricebasket.model.Item;

@Service
public class PriceBasketService {
	
	@Autowired
	public PriceBasketService () {}
	
	public int calculateSubTotal (Basket basket) {
		return basket.getItems()
				.stream()
				.mapToInt(i -> i.getPrice())
				.sum();
	}
	
	public int calculateDiscountedTotal (Basket basket) {
		return basket.getItems()
				.stream()
				.mapToInt(i -> i.getDiscountedPrice())
				.sum();
	}

	public List<Item> getDiscountedProducts(Basket basket) {
		return basket.getItems()
				.stream()
				.filter(i -> i.getDiscount().isPresent())
				.collect(Collectors.toList());
	}
}
