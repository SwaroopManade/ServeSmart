package edu.swaroop.servesmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.swaroop.servesmart.entity.Billing;
import edu.swaroop.servesmart.repository.BillingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    /**
     * Creates a new billing entry.
     *
     * @param billing The billing object to be added.
     * @return The added billing object.
     */
    public Billing createBilling(Billing billing) {
        return billingRepository.save(billing);
    }

    /**
     * Retrieves all billing entries.
     *
     * @return A list of all billing entries.
     */
    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    /**
     * Retrieves a billing entry by its ID.
     *
     * @param id The ID of the billing entry to retrieve.
     * @return The billing object if found, otherwise null.
     */
    public Billing getBillingById(int id) {
        Optional<Billing> billingOptional = billingRepository.findById(id);
        return billingOptional.orElse(null);
    }

    /**
     * Updates an existing billing entry.
     *
     * @param id      The ID of the billing entry to update.
     * @param billing The updated billing object.
     * @return The updated billing object.
     * @throws Exception if the billing entry is not found.
     */
    public Billing updateBilling(int id, Billing billing) throws Exception {
        if (!billingRepository.existsById(id)) {
            throw new Exception("Billing entry not found with ID: " + id);
        }
        billing.setId(id); // Ensure the ID is set for the update
        return billingRepository.save(billing);
    }

    /**
     * Deletes a billing entry by its ID.
     *
     * @param id The ID of the billing entry to delete.
     * @throws Exception if the billing entry is not found.
     */
    public void deleteBilling(int id) throws Exception {
        if (!billingRepository.existsById(id)) {
            throw new Exception("Billing entry not found with ID: " + id);
        }
        billingRepository.deleteById(id);
    }
}
