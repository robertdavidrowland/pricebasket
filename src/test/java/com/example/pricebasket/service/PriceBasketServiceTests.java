package com.example.pricebasket.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pricebasket.model.Basket;
import com.example.pricebasket.model.Item;
import com.example.pricebasket.model.Product;
import com.example.pricebasket.rules.ApplesWeeklyDiscountRule;
import com.example.pricebasket.rules.DiscountRule;
import com.example.pricebasket.rules.SoupMultibuyDiscountRule;

public class PriceBasketServiceTests {

	private static final Logger LOG = LoggerFactory.getLogger(Product.class);

	PriceBasketService priceBasketService;
	
	DiscountRule soupMultibuyDiscountRule;

	DiscountRule applesWeeklyDiscountRule;

	@Before
	public void setup() {
		priceBasketService = new PriceBasketService();
		soupMultibuyDiscountRule = new SoupMultibuyDiscountRule();
		applesWeeklyDiscountRule = new ApplesWeeklyDiscountRule();
	}
	
	@Test
	public void addProductsToBasket() {
		LOG.debug("addProductsToBasket");

		Basket basket = getFullBasket();
		applyDiscountRules(basket);
		
		assertEquals(4, basket.getItems().size());
	}
	
	@Test
	public void calculateSubTotalSingleItem() {
		LOG.debug("calculateSubTotalSingleItem");
				
		Basket basket = new Basket();
		basket.addItem(new Item(Product.SOUP));
		applyDiscountRules(basket);
		
		int subTotal = priceBasketService.calculateSubTotal(basket);
		
		assertEquals(65, subTotal);
	}
	
	@Test
	public void calculateSubTotalMultipleItems() {
		LOG.debug("calculateSubTotalMultipleItems");
				
		Basket basket = getFullBasket();
		applyDiscountRules(basket);
		
		int subTotal = priceBasketService.calculateSubTotal(basket);
		
		assertEquals(375, subTotal);
	}
	
	@Test
	public void calculateTotalSingleItem() {
		LOG.debug("calculateTotalSingleItem");
				
		Basket basket = new Basket();
		basket.addItem(new Item(Product.APPLES));
		applyDiscountRules(basket);
		
		int subTotal = priceBasketService.calculateDiscountedTotal(basket);
		
		assertEquals(90, subTotal);
	}
	
	@Test
	public void calculateTotalMultipleItems() {
		LOG.debug("calculateTotalMultipleItems");
				
		Basket basket = getFullBasket();
		applyDiscountRules(basket);
		
		int total = priceBasketService.calculateDiscountedTotal(basket);
		
		assertEquals(365, total);
	}
	
	@Test
	public void getDiscountedProducts() {
		LOG.debug("getDiscountedProducts");
				
		Basket basket = getFullBasket();
		applyDiscountRules(basket);
		
		List<Item> items = priceBasketService.getDiscountedProducts(basket);
		
		assertEquals(1, items.size());
		assertEquals(Product.APPLES, items.get(0).getProduct());
	}
	
	@Test
	public void calculateTotalWithMultBuyDiscount() {
		LOG.debug("calculateTotalWithMultBuyDiscount");
				
		Basket basket = getFullBasket();
		basket.addItem(new Item(Product.SOUP));
		applyDiscountRules(basket);

		int total = priceBasketService.calculateDiscountedTotal(basket);
		
		assertEquals(390, total);
	}
	
	@Test
	public void getDiscountedProductsWithMultBuyDiscount() {
		LOG.debug("getDiscountedProductsWithMultBuyDiscount");
				
		Basket basket = getFullBasket();
		basket.addItem(new Item(Product.SOUP));
		applyDiscountRules(basket);
		
		List<Item> items = priceBasketService.getDiscountedProducts(basket);
		
		assertEquals(2, items.size());
	}

	private void applyDiscountRules(Basket basket) {
		soupMultibuyDiscountRule.applyRuleToBasket(basket);
		applesWeeklyDiscountRule.applyRuleToBasket(basket);
	}
	
	private Basket getFullBasket() {
		Basket basket = new Basket();
		basket.addItem(new Item(Product.SOUP));
		basket.addItem(new Item(Product.BREAD));
		basket.addItem(new Item(Product.MILK));
		basket.addItem(new Item(Product.APPLES));
		
		return basket;
	}
}
