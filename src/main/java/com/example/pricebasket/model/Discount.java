package com.example.pricebasket.model;

public class Discount {
	
	private int discountPercentage;

	public Discount(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
}
