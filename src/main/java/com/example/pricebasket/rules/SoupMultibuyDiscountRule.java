package com.example.pricebasket.rules;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.pricebasket.model.Basket;
import com.example.pricebasket.model.Discount;
import com.example.pricebasket.model.Item;
import com.example.pricebasket.model.Product;

@Component
public class SoupMultibuyDiscountRule implements DiscountRule {

	public void applyRuleToBasket (Basket basket) {
		
		List<Item> soupItems = basket.getItems()
			.stream()
			.filter(i -> i.getProduct() == Product.SOUP)
			.collect(Collectors.toList());
		
		int noOfSoupItems = soupItems.size();
		
		int noOfMultiBuyDeals = noOfSoupItems / 2;
		
		AtomicInteger countOfDiscounts = new AtomicInteger(0);
		List<Item> updatedItems = basket.getItems()
			.stream()
			.map(i -> {
				if (i.getProduct() == Product.BREAD && countOfDiscounts.intValue() < noOfMultiBuyDeals) {
					i.setDiscount(Optional.of(new Discount(50)));
					countOfDiscounts.incrementAndGet();
				}
				return i;
	        })
			.collect(Collectors.toList());
		
		basket.setItems(updatedItems);
	}
}
