package edu.swaroop.servesmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.swaroop.servesmart.entity.Response;
import edu.swaroop.servesmart.entity.RestaurantTable;
import edu.swaroop.servesmart.service.TablesService;

import java.util.List;

@RestController
@RequestMapping("/tables") // Added base request mapping
@CrossOrigin(origins ="*")
public class TablesController {

    @Autowired
    private TablesService tablesService;

    /**
     * Adds a new table to the restaurant.
     *
     * @param restaurantTable The table object to be added.
     * @return ResponseEntity containing the added table information and status.
     */
    @PostMapping
    protected ResponseEntity<Response<RestaurantTable>> addTable(@RequestBody RestaurantTable restaurantTable) {
        Response<RestaurantTable> response = new Response<>();
        
        try {
            RestaurantTable tableToAdd = tablesService.addTable(restaurantTable);
            response.setMessage("New Table is Added");
            response.setStatus(HttpStatus.CREATED.value());
            response.setData(tableToAdd);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setMessage("Failed to add new table: " + e.getMessage());
            response.setStatus(HttpStatus.CONFLICT.value());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves a list of all tables.
     *
     * @return ResponseEntity containing the list of tables and status.
     */
    @GetMapping
    protected ResponseEntity<Response<List<RestaurantTable>>> getAllTables() {
        Response<List<RestaurantTable>> response = new Response<>();
        List<RestaurantTable> tables = tablesService.getAllTables();
        
        response.setMessage("List of all tables retrieved");
        response.setStatus(HttpStatus.OK.value());
        response.setData(tables);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves a table by its ID.
     *
     * @param id The ID of the table to retrieve.
     * @return ResponseEntity containing the table information and status.
     */
    @GetMapping("/{id}")
    protected ResponseEntity<Response<RestaurantTable>> getTableById(@PathVariable int id) {
        Response<RestaurantTable> response = new Response<>();
        RestaurantTable table = tablesService.getTableById(id);
        
        if (table != null) {
            response.setMessage("Table retrieved successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(table);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Table not found with ID: " + id);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing table.
     *
     * @param id The ID of the table to update.
     * @param restaurantTable The updated table object.
     * @return ResponseEntity containing the updated table information and status.
     */
    @PutMapping("/{id}")
    protected ResponseEntity<Response<RestaurantTable>> updateTable(@PathVariable int id, @RequestBody RestaurantTable restaurantTable) {
        Response<RestaurantTable> response = new Response<>();
        
        try {
            RestaurantTable updatedTable = tablesService.updateTable(id, restaurantTable);
            response.setMessage("Table updated successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(updatedTable);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Failed to update table: " + e.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a table by ID.
     *
     * @param id The ID of the table to delete.
     * @return ResponseEntity containing the status of the operation.
     */
    @DeleteMapping("/{id}")
    protected ResponseEntity<Response<String>> deleteTable(@PathVariable int id) {
        Response<String> response = new Response<>();
        
        try {
            tablesService.deleteTable(id);
            response.setMessage("Table deleted successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData("Deleted table with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Failed to delete table: " + e.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
