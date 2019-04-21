package com.github.springkit.dao;

import com.github.springkit.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByItemsQuantity(int quantity);
	
	@Query("{ \"items.quantity\": ?0 }")
	List<Order> findWithQuery(int quantity);
	
	@Query( value = "{ custInfo: ?0 }", fields = "{_id:0, items:1}")
	List<Order> findOnlyItems(String name);
}
