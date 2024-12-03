package edu.swaroop.servesmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.swaroop.servesmart.entity.Dish;
import edu.swaroop.servesmart.entity.Order;
import edu.swaroop.servesmart.entity.RestaurantTable;
import edu.swaroop.servesmart.repository.DishRepository;
//import edu.swaroop.servesmart.entity.Tables;
import edu.swaroop.servesmart.repository.OrderRepository;
import edu.swaroop.servesmart.repository.TablesRepository;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TablesRepository tablesRepository;
    @Autowired
    private DishRepository dishRepository;
    
    
    public Order addOrder(Order order) {
       
        RestaurantTable table = tablesRepository.findById(order.getTable().getId())
                .orElseThrow(() -> new RuntimeException("Table not found with ID: " + order.getTable().getId()));

        List<Dish> verifiedDishes = new ArrayList<>();

        for (Dish dish : order.getDishes()) {
 
            Dish verifiedDish = dishRepository.findById(dish.getId())
                    .orElseThrow(() -> new RuntimeException("Dish not found with ID: " + dish.getId()));

         
            verifiedDishes.add(verifiedDish);
        }

        order.setTable(table);
        order.setDishes(verifiedDishes);
        return orderRepository.save(order);
    }


    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    
    public List<Order> getOrdersByTableId(int tableId) {
        return orderRepository.findByTableId(tableId); // You might need to create this method in your repository
    }


    public Order getOrderById(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }

    
    public Order updateOrder(int id, Order order) {
       
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
        
        existingOrder.setDishes(order.getDishes()); 
        existingOrder.setTable(order.getTable());           

       
        return orderRepository.save(existingOrder);
    }




    public void deleteOrder(int id) throws Exception {
        if (!orderRepository.existsById(id)) {
            throw new Exception("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }
    
    public void deleteOrdersByTableId(int tableId) {
        List<Order> orders = orderRepository.findByTableId(tableId);
        for (Order order : orders) {
            orderRepository.delete(order);
        }
    }


    public Order updateOrderByTableId(int tableId, Order order) {
   
        List<Order> existingOrders = orderRepository.findByTableId(tableId);
        
        if (!existingOrders.isEmpty()) {
            Order existingOrder = existingOrders.get(0); 
            existingOrder.setDishes(order.getDishes()); 
          
            return orderRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found for table ID: " + tableId);
        }
    }

}
