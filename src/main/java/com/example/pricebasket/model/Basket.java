package com.example.pricebasket.model;

import java.util.ArrayList;
import java.util.List;

import com.example.pricebasket.rules.SoupMultibuyDiscountRule;

public class Basket {

	List<Item> items = new ArrayList<>();

	List<SoupMultibuyDiscountRule> discountRules = new ArrayList<>(); 

	public Basket() {}
	
	public Basket(List<Item> items) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Item product) {
		items.add(product);
	}

	public List<SoupMultibuyDiscountRule> getDiscountRules() {
		return discountRules;
	}

	public void setDiscountRules(List<SoupMultibuyDiscountRule> discountRules) {
		this.discountRules = discountRules;
	}
	
	public void addDiscountRule(SoupMultibuyDiscountRule discountRule) {
		discountRules.add(discountRule);
	}
}
