package com.dm4nk.customer.impl.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dm4nk.customer.api.mappers.CustomerMapper;
import com.dm4nk.customer.api.service.CustomerService;
import com.dm4nk.customer.impl.graphql.inputs.AuthorizationInput;
import com.dm4nk.customer.impl.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Validated
public class CustomerQuery implements GraphQLQueryResolver {

    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    public Optional<Customer> getCustomer(final UUID id) {
        return customerService.getCustomerById(id);
    }

    public Optional<Customer> authorize(final @Valid AuthorizationInput authorizationInput){
        Customer customer = customerMapper.authorizationInputToCustomer(authorizationInput);
        return customerService.authorizeCustomer(customer);
    }
}
