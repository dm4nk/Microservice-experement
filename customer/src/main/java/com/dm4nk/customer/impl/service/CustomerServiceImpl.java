package com.dm4nk.customer.impl.service;

import com.dm4nk.customer.api.exceptions.ExceptionFactory;
import com.dm4nk.customer.api.repository.CustomerRepository;
import com.dm4nk.customer.api.service.CustomerService;
import com.dm4nk.customer.impl.model.Customer;
import com.dm4nk.customer.impl.model.Message;
import com.dm4nk.customer.impl.util.constants.Constants;
import com.dm4nk.customer.impl.util.enums.ExceptionTypes;
import com.dm4nk.shared.request.fraud.FraudCheckRequest;
import com.dm4nk.shared.request.fraud.FraudRequest;
import com.dm4nk.shared.response.fraud.FraudCheckResponse;
import com.dm4nk.shared.response.fraud.FraudResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final ExceptionFactory exceptionFactory;

    private final UriComponentsBuilder checkUriBuilder;

    private final UriComponentsBuilder fraudUriBuilder;

    @Override
    @Transactional
    public Customer registerCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        URI uri = checkUriBuilder
                .queryParam(Constants.Query.CUSTOMER_ID, savedCustomer.getId())
                .build()
                .toUri();

        FraudCheckRequest request = FraudCheckRequest.builder()
                .customerId(savedCustomer.getId())
                .build();

        //todo add validations
        //todo check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.postForObject(
                uri,
                request,
                FraudCheckResponse.class);

        if (requireNonNull(fraudCheckResponse).isFraudster()) {
            throw exceptionFactory.generateException(ExceptionTypes.FRAUD, customer.getId());
        }

        //todo send notification

        return savedCustomer;
    }

    @Override
    @Transactional
    public Message banCustomer(UUID id) {
        URI uri = fraudUriBuilder.queryParam(Constants.Query.CUSTOMER_ID, id)
                .build()
                .toUri();

        FraudRequest request = FraudRequest.builder()
                .customerId(id)
                .build();

        FraudResponse fraudResponse = restTemplate.postForObject(
                uri,
                request,
                FraudResponse.class);

        log.debug("Got response: {}", fraudResponse);

        return Message.builder().message(fraudResponse.message()).build();
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> authorizeCustomer(Customer customer) {
        return customerRepository.findByEmail(customer.getEmail())
                .filter(customerFromDB ->
                        customerFromDB.getPassword().equals(customer.getPassword()));
    }

    @Override
    public String test(String firstArg, String secondArg) {
        return firstArg + secondArg;
    }
}
