package com.dm4nk.customer.api.repository;

import com.dm4nk.customer.impl.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
