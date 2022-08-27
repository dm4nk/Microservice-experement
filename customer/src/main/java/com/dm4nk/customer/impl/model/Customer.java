package com.dm4nk.customer.impl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
}
