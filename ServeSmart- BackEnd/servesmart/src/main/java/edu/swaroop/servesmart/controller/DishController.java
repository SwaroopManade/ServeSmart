package edu.swaroop.servesmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.swaroop.servesmart.entity.Dish;
import edu.swaroop.servesmart.entity.Response;
import edu.swaroop.servesmart.service.DishService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins ="*")
public class DishController {

    @Autowired
    private DishService dishService;

    // Add a new dish
    @PostMapping(value = "/dish")
    protected ResponseEntity<Response<Dish>> addDish(@RequestBody Dish dish) {
        Dish newDish = dishService.addDish(dish);
        Response<Dish> response = new Response<>();
        response.setMessage("Dish added successfully");
        response.setStatus(HttpStatus.OK.value());
        response.setData(newDish);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all dishes
    @GetMapping(value = "/dishes")
    protected ResponseEntity<Response<List<Dish>>> getAllDishes() {
        List<Dish> dishes = dishService.getAllDishes();
        Response<List<Dish>> response = new Response<>();
        response.setMessage("Dishes retrieved successfully");
        response.setStatus(HttpStatus.OK.value());
        response.setData(dishes);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a dish by ID
    @GetMapping(value = "/dish/{id}")
    protected ResponseEntity<Response<Dish>> getDishById(@PathVariable("id") int id) {
        Dish dish = dishService.getDishById(id);
        Response<Dish> response = new Response<>();
        if (dish != null) {
            response.setMessage("Dish retrieved successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(dish);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Dish not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Update a dish by ID
    @PutMapping(value = "/dish/{id}")
    protected ResponseEntity<Response<Dish>> updateDish(@PathVariable("id") int id, @RequestBody Dish dishDetails) {
        Dish updatedDish = dishService.updateDish(id, dishDetails);
        Response<Dish> response = new Response<>();
        if (updatedDish != null) {
            response.setMessage("Dish updated successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(updatedDish);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Dish not found for update");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

 // Delete a dish by ID
    @DeleteMapping(value = "/dish/{id}")
    protected ResponseEntity<Response<String>> deleteDish(@PathVariable int id) {
        boolean isDeleted = dishService.deleteDish(id); // Service method now returns boolean
        Response<String> response = new Response<>();

        if (isDeleted) {
            response.setMessage("Dish deleted successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData("Dish with ID " + id + " has been deleted.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Dish not found for deletion");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setData("Failed to delete dish with ID " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
