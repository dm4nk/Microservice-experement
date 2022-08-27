package com.dm4nk.customer.impl.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dm4nk.customer.api.service.CustomerService;
import com.dm4nk.customer.impl.model.Customer;
import com.dm4nk.customer.impl.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerMutation implements GraphQLMutationResolver {
    private final CustomerService customerService;

    public Customer registerCustomer(String firstName, String lastName, String email) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
        return customerService.registerCustomer(customer);
    }

    public Message banCustomer(UUID id) {
        return customerService.banCustomer(id);
    }
}
