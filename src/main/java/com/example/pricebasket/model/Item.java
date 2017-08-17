package com.example.pricebasket.model;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pricebasket.exception.ProductNotFoundException;

public class Item {
		
	private static final Logger LOG = LoggerFactory.getLogger(Product.class);

	private Product product;
	private Optional<Discount> discount;

	public Item(Product product) {
		this.product = product;
		this.discount = Optional.empty();
	}
	
	public Item(String productName) throws ProductNotFoundException {
		this.product = Product.getProductByName(productName);
		this.discount = Optional.empty();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Optional<Discount> getDiscount() {
		return discount;
	}

	public void setDiscount(Optional<Discount> discount) {
		this.discount = discount;
	}

	public int getPrice() {
		return product.getPrice();
	}
	
	public int getDiscountPercentage() {
		int discountedPercentage = 0;

		if (discount.isPresent()) {
			discountedPercentage = discount.get().getDiscountPercentage();
		}
		
		return discountedPercentage;
	}

	public int getDiscountValue() {
		int discountedValue = this.product.getPrice();

		if (discount.isPresent()) {
			discountedValue = getDiscountInPence(this.product.getPrice(), discount);
		}
		
		return discountedValue;
	}

	public int getDiscountedPrice() {
		int discountedPrice = this.product.getPrice();

		if (discount.isPresent()) {
			discountedPrice = applyDiscount(discountedPrice, discount);
		}
		
		return discountedPrice;
	}
	
	private int applyDiscount(int price, Optional<Discount> discount) {
		int p = price;
		int d = discount.get().getDiscountPercentage();
		
		LOG.debug("apply discount {} - (({} * {}) / 100 = {}", p, p, discount, p - getDiscountInPence(price, discount));
		return p - getDiscountInPence(price, discount);
	}
	
	private int getDiscountInPence(int price, Optional<Discount> discount) {
		int p = price;
		int d = discount.get().getDiscountPercentage();
		
		LOG.debug("get discount in pence ({} * {}) / 100 = {}", p, d, (p * d) / 100);
		return (p * d) / 100;
	}
}
