package com.example.pricebasket.rules;

import com.example.pricebasket.model.Basket;

public interface DiscountRule {
	public void applyRuleToBasket (Basket basket);
}
