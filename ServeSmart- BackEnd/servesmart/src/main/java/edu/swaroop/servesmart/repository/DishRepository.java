package edu.swaroop.servesmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.swaroop.servesmart.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {

}
