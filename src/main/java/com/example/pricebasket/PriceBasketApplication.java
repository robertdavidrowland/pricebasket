package com.example.pricebasket;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.pricebasket.exception.ProductNotFoundException;
import com.example.pricebasket.model.Basket;
import com.example.pricebasket.model.Item;
import com.example.pricebasket.model.Product;
import com.example.pricebasket.rules.DiscountRule;
import com.example.pricebasket.service.PriceBasketService;

@SpringBootApplication
public class PriceBasketApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(PriceBasketApplication.class);

    @Autowired
	PriceBasketService priceBasketService;  

    @Autowired
	@Qualifier("soupMultibuyDiscountRule")
	DiscountRule soupMultibuyDiscountRule;

    @Autowired
	@Qualifier("applesWeeklyDiscountRule")
	DiscountRule applesWeeklyDiscountRule;
    
	@Override
	public void run(String... args) {

		// setup our basket of items, exit with an message if any items are invalid
		Basket basket = new Basket();
		try {
			basket.setItems(
					Arrays.asList(args)
						.stream()
						.map(p -> new Item(Product.getProductByName(p)))
						.collect(Collectors.toList()));
		}
		catch (ProductNotFoundException e) {
			PriceBasketApplication.output(e.getMessage());
			System.exit(0);
		}
		
		// exit with an message if the basket is empty
		if (basket.getItems().size() == 0) {
			PriceBasketApplication.output("useage: java -jar priceBasket-0.0.1-SNAPSHOT.jar Apples Bread Soup Milk");
			return;
		}
		
		// apply any discounts
		applesWeeklyDiscountRule.applyRuleToBasket(basket);
		soupMultibuyDiscountRule.applyRuleToBasket(basket);
		
		// calculate and display sub-total, discounts and total
		int subTotal = priceBasketService.calculateSubTotal(basket);
		int discountedTotal = priceBasketService.calculateDiscountedTotal(basket);
		List<Item> discountedItems = priceBasketService.getDiscountedProducts(basket);
		
		PriceBasketApplication.output(String.format("Subtotal: %d.%d", subTotal / 100, subTotal % 100));
		for (Item discountedItem : discountedItems) {
			PriceBasketApplication.output(String.format("%s %d%% off: -%dp", discountedItem.getProduct().getName(), discountedItem.getDiscountPercentage(), discountedItem.getDiscountValue()));
		}
		PriceBasketApplication.output(String.format("Total: %d.%d", discountedTotal / 100, discountedTotal % 100));
	}
	
	private static void output (String output) {
		LOG.debug(output);
		System.out.println(output);		
	}
	
	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PriceBasketApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);        
	}
}
