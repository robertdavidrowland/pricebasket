package com.example.pricebasket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.pricebasket.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceBasketApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(Product.class);

	@Autowired
	PriceBasketApplication app;

	@Test
	public void priceBasket() {
		LOG.debug("priceBasket");
		
//	    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//	    StatusPrinter.print(lc);
		
		app.run("Apples", "Milk", "Bread", "Soup", "Soup");
	}
}
