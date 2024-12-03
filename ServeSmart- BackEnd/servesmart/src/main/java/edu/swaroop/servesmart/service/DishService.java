package edu.swaroop.servesmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.swaroop.servesmart.entity.Dish;
import edu.swaroop.servesmart.repository.DishRepository;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    // Add a new dish
    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    // Get all dishes
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    // Find a dish by id
    public Dish getDishById(int id) {
        return dishRepository.findById(id).orElse(null);
    }

    // Update an existing dish
    public Dish updateDish(int id, Dish dishDetails) {
        Dish dish = getDishById(id);
        if (dish != null) {
            dish.setName(dishDetails.getName());
            dish.setPrice(dishDetails.getPrice());
            return dishRepository.save(dish);
        }
        return null; // or throw an exception
    }

 // Delete a dish by ID with a boolean return type
    public boolean deleteDish(int id) {
        if (dishRepository.existsById(id)) { // Check if the dish exists
            dishRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
