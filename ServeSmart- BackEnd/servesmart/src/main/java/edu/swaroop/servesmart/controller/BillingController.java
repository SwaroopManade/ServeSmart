package edu.swaroop.servesmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.swaroop.servesmart.entity.Billing;
import edu.swaroop.servesmart.entity.Response;
import edu.swaroop.servesmart.service.BillingService;

import java.util.List;

@RestController
@RequestMapping("/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    /**
     * Creates a new billing entry.
     *
     * @param billing The billing object to be added.
     * @return ResponseEntity containing the added billing entry.
     */
    @PostMapping
    protected ResponseEntity<Response<Billing>> createBilling(@RequestBody Billing billing) {
        Billing createdBilling = billingService.createBilling(billing);
        Response<Billing> response = new Response<>();
        response.setMessage("Billing entry created successfully.");
        response.setStatus(HttpStatus.CREATED.value());
        response.setData(createdBilling);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all billing entries.
     *
     * @return ResponseEntity containing a list of all billing entries.
     */
    @GetMapping
    protected ResponseEntity<Response<List<Billing>>> getAllBillings() {
        List<Billing> billings = billingService.getAllBillings();
        Response<List<Billing>> response = new Response<>();
        response.setMessage("Billings retrieved successfully.");
        response.setStatus(HttpStatus.OK.value());
        response.setData(billings);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves a billing entry by its ID.
     *
     * @param id The ID of the billing entry to retrieve.
     * @return ResponseEntity containing the billing entry if found.
     */
    @GetMapping("/{id}")
    protected ResponseEntity<Response<Billing>> getBillingById(@PathVariable int id) {
        Billing billing = billingService.getBillingById(id);
        Response<Billing> response = new Response<>();

        if (billing != null) {
            response.setMessage("Billing entry retrieved successfully.");
            response.setStatus(HttpStatus.OK.value());
            response.setData(billing);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Billing entry not found.");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing billing entry.
     *
     * @param id      The ID of the billing entry to update.
     * @param billing The updated billing object.
     * @return ResponseEntity containing the updated billing entry.
     * @throws Exception if the billing entry is not found.
     */
    @PutMapping("/{id}")
    protected ResponseEntity<Response<Billing>> updateBilling(@PathVariable int id, @RequestBody Billing billing) throws Exception {
        Billing updatedBilling = billingService.updateBilling(id, billing);
        Response<Billing> response = new Response<>();
        response.setMessage("Billing entry updated successfully.");
        response.setStatus(HttpStatus.OK.value());
        response.setData(updatedBilling);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a billing entry by its ID.
     *
     * @param id The ID of the billing entry to delete.
     * @return ResponseEntity indicating success or failure.
     * @throws Exception if the billing entry is not found.
     */
    @DeleteMapping("/{id}")
    protected ResponseEntity<Response<String>> deleteBilling(@PathVariable int id) throws Exception {
        billingService.deleteBilling(id);
        Response<String> response = new Response<>();
        response.setMessage("Billing entry deleted successfully.");
        response.setStatus(HttpStatus.OK.value());
        response.setData("Billing entry with ID " + id + " has been deleted.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
