package com.springBatch.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Products {

	private Integer productId;
	private String productCode;
	private Double productCost;
	
	
	private Double productDiscount;
	private Double productGst;
	public void setProductGst(double productGst2) {
		// TODO Auto-generated method stub
		
	}
}
