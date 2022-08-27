package com.dm4nk.customer.api.service;

import com.dm4nk.customer.impl.model.Customer;
import com.dm4nk.customer.impl.model.Message;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Customer registerCustomer(Customer customer);

    Message banCustomer(UUID id);

    Optional<Customer> getCustomerById(UUID id);

    Optional<Customer> authorizeCustomer(Customer customer);

    String test(String firstArg, String secondArg);
}
