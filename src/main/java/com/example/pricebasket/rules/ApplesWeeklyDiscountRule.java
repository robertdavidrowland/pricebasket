package com.example.pricebasket.rules;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.pricebasket.model.Basket;
import com.example.pricebasket.model.Discount;
import com.example.pricebasket.model.Item;
import com.example.pricebasket.model.Product;

@Component
public class ApplesWeeklyDiscountRule implements DiscountRule {

	public void applyRuleToBasket (Basket basket) {

		List<Item> updatedItems = basket.getItems()
			.stream()
			.map(i -> {
				if (i.getProduct() == Product.APPLES) {
					i.setDiscount(Optional.of(new Discount(10)));
				}
				return i;
	        })
			.collect(Collectors.toList());
		
		basket.setItems(updatedItems);
	}
}
