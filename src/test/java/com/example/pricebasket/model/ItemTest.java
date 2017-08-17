package com.example.pricebasket.model;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pricebasket.exception.ProductNotFoundException;

public class ItemTest {

	private static final Logger LOG = LoggerFactory.getLogger(ItemTest.class);
	
	@Test
	public void setupItemByProduct() {
		LOG.debug("setupItemByProduct");
		
		Item item = new Item(Product.APPLES);
		assertEquals("Apples", item.getProduct().getName());
	}
	
	@Test
	public void setupItemByName() throws ProductNotFoundException {
		LOG.debug("setupItemByName");
		
		Item item = new Item("Apples");
		assertEquals("Apples", item.getProduct().getName());
	}

	@Test
	public void getProductDiscountPriceWithNoDiscount() throws ProductNotFoundException {
		LOG.error("getProductDiscountPriceWithNoDiscount");
		
		Item item = new Item("Soup");
		assertEquals(65, item.getProduct().getPrice());
	}
	
	@Test
	public void getProductDiscountPriceWithAssignedDiscount1() throws ProductNotFoundException {
		LOG.debug("getProductDiscountPriceWithDefaultDiscount");
		
		Item item = new Item("Apples");
		item.setDiscount(Optional.of(new Discount(10)));
		
		assertEquals(90, item.getDiscountedPrice());
	}
	
	@Test
	public void getProductDiscountPriceWithAssignedDiscount2() throws ProductNotFoundException {
		LOG.debug("getProductDiscountPriceWithAssignedDiscount");
		
		Item item = new Item("Bread");
		item.setDiscount(Optional.of(new Discount(50)));
		
		assertEquals(40, item.getDiscountedPrice());
	}
}
