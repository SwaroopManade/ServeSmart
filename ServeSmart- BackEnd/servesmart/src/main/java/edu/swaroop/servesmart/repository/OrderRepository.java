package edu.swaroop.servesmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.swaroop.servesmart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	 List<Order> findByTableId(int tableId); 
}
