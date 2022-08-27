package com.dm4nk.customer.impl.graphql.inputs;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationInput {
    @NotBlank(message = "firstname is required")
    @Length(max = 255, message = "Too long for name")
    String firstname;

    @NotBlank(message = "lastname is required")
    @Length(max = 255, message = "Too long for surname")
    String lastname;

    @NotBlank(message = "email is required")
    @Length(max = 255, message = "Too long for email")
    @Email(message = "Not an email")
    String email;

    @NotBlank(message = "Password is required")
    @Length(max = 16, message = "Password length must be less then 16 symbols")
    String password;
}
