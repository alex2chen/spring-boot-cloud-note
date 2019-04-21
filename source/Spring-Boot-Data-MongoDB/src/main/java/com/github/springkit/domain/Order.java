package com.github.springkit.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

public class Order {

	@Id
    private String id;
	
	private Date date;
	
	@Field("custInfo") private String customerInfo;
	
	List<Item> items;
	
	public Order() {
		this(null);
	}

	public Order(String customerInfo) {
		super();
		this.customerInfo = customerInfo;
	}

	public Order(String id, String customerInfo) {
		this(customerInfo);
		this.id = id;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
	
}
