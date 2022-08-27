package com.dm4nk.customer.api.service;

import com.dm4nk.customer.impl.model.Customer;
import com.dm4nk.customer.impl.model.Message;
import com.dm4nk.shared.request.customer.BanCustomerRequest;
import com.dm4nk.shared.request.customer.CustomerRegistrationRequest;
import com.dm4nk.shared.response.fraud.FraudResponse;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest);

    Customer registerCustomer(Customer customer);

    FraudResponse banCustomer(BanCustomerRequest banCustomerRequest);

    Message banCustomer(UUID id);

    Optional<Customer> getCustomerById(UUID id);

    String test(String firstArg, String secondArg);
}
