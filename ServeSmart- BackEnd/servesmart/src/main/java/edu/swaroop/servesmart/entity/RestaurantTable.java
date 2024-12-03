package edu.swaroop.servesmart.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant_table") // Avoid using `tables` since it's a reserved keyword in SQL
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "table_number", nullable = false, unique = true)
    private int tableNumber;

    @Column(name = "status", nullable = false)
    private String status; // e.g., "occupied", "vacant", etc.

    @Column(name = "capacity", nullable = false)
    private int capacity; // Optional: Indicates how many people the table can seat

    // Optional: Bidirectional relationship with Order entity if needed
     @OneToMany(mappedBy = "table")
     private List<Order> orders;

    // No-argument constructor
    public RestaurantTable() {}

    // Parameterized constructor
    public RestaurantTable(int tableNumber, String status, int capacity) {
        this.tableNumber = tableNumber;
        this.status = status;
        this.capacity = capacity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "id=" + id +
                ", tableNumber=" + tableNumber +
                ", status='" + status + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
