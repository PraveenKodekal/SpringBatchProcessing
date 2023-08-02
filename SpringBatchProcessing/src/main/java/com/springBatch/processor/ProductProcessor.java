package com.springBatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.springBatch.entity.Products;

public class ProductProcessor implements ItemProcessor<Products, Products> {

	@Override
	public Products process(Products item) throws Exception {

		double cost=item.getProductCost();
		item.setProductDiscount(cost*12/100);
		item.setProductGst(cost*18/100);
		return item;
	}

}
