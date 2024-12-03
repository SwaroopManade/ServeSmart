package edu.swaroop.servesmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.swaroop.servesmart.entity.RestaurantTable;
//import edu.swaroop.servesmart.entity.Tables;

public interface TablesRepository extends JpaRepository<RestaurantTable, Integer> {

}
