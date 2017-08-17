package com.example.pricebasket.rules;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pricebasket.model.Basket;
import com.example.pricebasket.model.Item;
import com.example.pricebasket.model.Product;

public class DiscountRuleServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(DiscountRuleServiceTest.class);
	
	DiscountRule soupMultibuyDiscountRule;

	DiscountRule applesWeeklyDiscountRule;

	@Before
	public void setup() {
		soupMultibuyDiscountRule = new SoupMultibuyDiscountRule();
		applesWeeklyDiscountRule = new ApplesWeeklyDiscountRule();
	}
	
	@Test
	public void applySoupMultiBuyDiscountRule () {
		LOG.debug("applySoupMultiBuyDiscountRule");
				
		Basket basket = getFullBasket();
		basket.addItem(new Item(Product.SOUP));	
		applyDiscountRules(basket);
		
		// we should find a discounted loaf of bread in this basket
		List<Item> discountedItems = basket.getItems().stream().filter(i -> i.getProduct() == Product.BREAD).collect(Collectors.toList());
		
		assertEquals(1, discountedItems.size());
		assertEquals(50, discountedItems.get(0).getDiscount().get().getDiscountPercentage());
	}
	
	@Test
	public void applyApplesWeeklyDiscountRule () {
		LOG.debug("applyMultiBuyDiscountRule");
				
		Basket basket = getFullBasket();
		applyDiscountRules(basket);
		
		// we should find a discounted loaf of bread in this basket
		List<Item> discountedItems = basket.getItems().stream().filter(i -> i.getProduct() == Product.APPLES).collect(Collectors.toList());
		
		assertEquals(1, discountedItems.size());
		assertEquals(10, discountedItems.get(0).getDiscount().get().getDiscountPercentage());
	}
	
	@Test
	public void doNotApplySoupMultiBuyDiscountRule () {
		LOG.debug("doNotApplySoupMultiBuyDiscountRule");
		
		Basket basket = getFullBasket();
		applyDiscountRules(basket);
		
		// we should find no discounted bread as there is only one can of soup!
		List<Item> discountedItems = basket.getItems().stream().filter(i -> i.getProduct() == Product.BREAD).collect(Collectors.toList());
		
		assertEquals(1, discountedItems.size());
		assertEquals(false, discountedItems.get(0).getDiscount().isPresent());
	}
	
	@Test
	public void doApplySomeSoupMultiBuyDiscountRule () {
		LOG.debug("doNotApplySoupMultiBuyDiscountRule");
		
		Basket basket = getFullBasket();
		basket.addItem(new Item(Product.SOUP));	
		basket.addItem(new Item(Product.BREAD));	
		applyDiscountRules(basket);
		
		// this basket has two soups and two breads . . only one of the breads should be discounted
		List<Item> discountedItems = basket.getItems().stream().filter(i -> i.getProduct() == Product.BREAD).collect(Collectors.toList());
		
		assertEquals(2, discountedItems.size());
		assertEquals(50, discountedItems.get(0).getDiscount().get().getDiscountPercentage());
		assertEquals(false, discountedItems.get(1).getDiscount().isPresent());
	}
	
	private Basket getFullBasket() {
		Basket basket = new Basket();
		basket.addItem(new Item(Product.SOUP));
		basket.addItem(new Item(Product.BREAD));
		basket.addItem(new Item(Product.MILK));
		basket.addItem(new Item(Product.APPLES));
		
		return basket;
	}
	
	private void applyDiscountRules(Basket basket) {
		soupMultibuyDiscountRule.applyRuleToBasket(basket);
		applesWeeklyDiscountRule.applyRuleToBasket(basket);
	}
}
