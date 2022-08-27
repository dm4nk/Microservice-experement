package com.dm4nk.customer.impl.graphql.inputs;

import lombok.Data;

@Data
public class AuthorizationInput {
    private String email;
    private String password;
}
