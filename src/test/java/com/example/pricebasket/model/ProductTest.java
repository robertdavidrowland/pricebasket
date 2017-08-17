package com.example.pricebasket.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pricebasket.exception.ProductNotFoundException;

public class ProductTest {

	private static final Logger LOG = LoggerFactory.getLogger(Product.class);

	@Test
	public void getProductPrice() {
		LOG.debug("getProductPrice");
		
		Product apples = Product.APPLES;
		assertEquals(100, apples.getPrice());
	}
	
	@Test
	public void getProductDiscountPriceWithNoDiscount() {
		LOG.error("getProductDiscountPriceWithNoDiscount");
		
		Product soup = Product.SOUP;
		assertEquals(65, soup.getPrice());
	}
	
	@Test
	public void getProductByName() throws ProductNotFoundException {
		LOG.debug("getProductByName");
		
		assertEquals("Apples", Product.getProductByName("Apples").getName());
	}
	
	@Test
	public void getOtherProductByName() throws ProductNotFoundException {
		LOG.debug("getOtherProductByName");
		
		assertEquals("Bread", Product.getProductByName("Bread").getName());
	}
	
	@Test(expected=ProductNotFoundException.class)
	public void errorOnIllegalProductByName() throws ProductNotFoundException {
		LOG.debug("errorOnIllegalProductByName");
		
		assertEquals("Bread", Product.getProductByName("Not a product").getName());
	}
}
