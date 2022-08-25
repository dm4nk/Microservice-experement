package com.dm4nk.customer.api.service;

import com.dm4nk.shared.request.customer.BanCustomerRequest;
import com.dm4nk.shared.request.customer.CustomerRegistrationRequest;
import com.dm4nk.shared.response.fraud.FraudResponse;

import javax.transaction.Transactional;

public interface CustomerService {
    @Transactional
    void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest);

    FraudResponse banCustomer(BanCustomerRequest banCustomerRequest);

    String test(String firstArg, String secondArg);
}
