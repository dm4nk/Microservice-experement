package com.dm4nk.customer.api.mappers;

import com.dm4nk.customer.impl.graphql.inputs.AuthorizationInput;
import com.dm4nk.customer.impl.graphql.inputs.RegistrationInput;
import com.dm4nk.customer.impl.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer registrationInputToCustomer(RegistrationInput registrationInput);

    Customer authorizationInputToCustomer(AuthorizationInput authorizationInput);
}
