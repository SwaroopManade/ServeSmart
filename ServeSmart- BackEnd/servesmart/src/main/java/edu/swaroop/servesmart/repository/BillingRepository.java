package edu.swaroop.servesmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import edu.swaroop.servesmart.entity.Bill;
import edu.swaroop.servesmart.entity.Billing;

public interface BillingRepository extends JpaRepository<Billing, Integer> {

}
