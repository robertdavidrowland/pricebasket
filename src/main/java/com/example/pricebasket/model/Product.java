package com.example.pricebasket.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pricebasket.exception.ProductNotFoundException;

public enum Product {
    
	SOUP("Soup", 65),
	BREAD("Bread", 80),
	MILK("Milk", 130),
	APPLES("Apples", 100);
	
	private static final Logger LOG = LoggerFactory.getLogger(Product.class);

    private static final Map<String, Product> lookup = new HashMap<>();

    static {
        for (Product p : Product.values()) {
            lookup.put(p.getName(), p);
        }
    }
	
	public static Product getProductByName(String name) throws ProductNotFoundException {
		
		Product foundProduct = lookup.get(name);
		
		if (foundProduct == null) {
			throw new ProductNotFoundException(String.format("could not find product %s", name));
		}
		
	    return foundProduct;
	}
	
	private String name;
	private int price;
    
	private Product(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}
