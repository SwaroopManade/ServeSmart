package edu.swaroop.servesmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.swaroop.servesmart.entity.RestaurantTable;
import edu.swaroop.servesmart.repository.TablesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TablesService {

    @Autowired
    private TablesRepository tablesRepository;

    /**
     * Adds a new table to the restaurant.
     *
     * @param restaurantTable The table object to be added.
     * @return The added table object.
     */
    public RestaurantTable addTable(RestaurantTable restaurantTable) {
        return tablesRepository.save(restaurantTable);
    }

    /**
     * Retrieves all tables in the restaurant.
     *
     * @return A list of all tables.
     */
    public List<RestaurantTable> getAllTables() {
        return tablesRepository.findAll();
    }

    /**
     * Retrieves a table by its ID.
     *
     * @param id The ID of the table to retrieve.
     * @return The table object if found, otherwise null.
     */
    public RestaurantTable getTableById(int id) {
        Optional<RestaurantTable> tableOptional = tablesRepository.findById(id);
        return tableOptional.orElse(null);
    }

    /**
     * Updates an existing table.
     *
     * @param id                The ID of the table to update.
     * @param restaurantTable   The updated table object.
     * @return The updated table object.
     * @throws Exception if the table is not found.
     */
    public RestaurantTable updateTable(int id, RestaurantTable restaurantTable) throws Exception {
        if (!tablesRepository.existsById(id)) {
            throw new Exception("Table not found with ID: " + id);
        }
        restaurantTable.setId(id); // Ensure the ID is set for the update
        return tablesRepository.save(restaurantTable);
    }

    /**
     * Deletes a table by its ID.
     *
     * @param id The ID of the table to delete.
     * @throws Exception if the table is not found.
     */
    public void deleteTable(int id) throws Exception {
        if (!tablesRepository.existsById(id)) {
            throw new Exception("Table not found with ID: " + id);
        }
        tablesRepository.deleteById(id);
    }
}
