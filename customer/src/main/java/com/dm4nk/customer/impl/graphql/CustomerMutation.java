package com.dm4nk.customer.impl.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dm4nk.customer.api.service.CustomerService;
import com.dm4nk.customer.impl.graphql.inputs.RegistrationInput;
import com.dm4nk.customer.impl.model.Customer;
import com.dm4nk.customer.impl.model.Message;
import com.dm4nk.customer.api.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Validated
public class CustomerMutation implements GraphQLMutationResolver {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public Customer registerCustomer(@Valid final RegistrationInput registrationInput) {
        Customer customer = customerMapper.registrationInputToCustomer(registrationInput);
        return customerService.registerCustomer(customer);
    }

    public Message banCustomer(final UUID id) {
        return customerService.banCustomer(id);
    }
}
