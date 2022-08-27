package com.dm4nk.customer.impl.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dm4nk.customer.api.service.CustomerService;
import com.dm4nk.customer.impl.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerQuery implements GraphQLQueryResolver {

    private final CustomerService customerService;

    public Optional<Customer> getCustomer(final UUID id) {
        return customerService.getCustomerById(id);
    }
}
