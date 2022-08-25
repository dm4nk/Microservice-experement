package com.dm4nk.customer.impl.service;

import com.dm4nk.customer.api.exceptions.ExceptionFactory;
import com.dm4nk.customer.impl.util.enums.ExceptionTypes;
import com.dm4nk.customer.impl.util.constants.Constants;
import com.dm4nk.customer.impl.model.Customer;
import com.dm4nk.customer.api.repository.CustomerRepository;
import com.dm4nk.shared.request.customer.BanCustomerRequest;
import com.dm4nk.shared.request.customer.CustomerRegistrationRequest;
import com.dm4nk.shared.request.fraud.FraudCheckRequest;
import com.dm4nk.shared.request.fraud.FraudRequest;
import com.dm4nk.shared.response.fraud.FraudCheckResponse;
import com.dm4nk.shared.response.fraud.FraudResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements com.dm4nk.customer.api.service.CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final ExceptionFactory exceptionFactory;

    private final UriComponentsBuilder checkUriBuilder;

    private final UriComponentsBuilder fraudUriBuilder;

    @Override
    @Transactional
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .email(customerRegistrationRequest.email())
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .build();

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
    }

    @Override
    public FraudResponse banCustomer(BanCustomerRequest banCustomerRequest) {
        log.debug("Gor request: {}", banCustomerRequest);

        URI uri = fraudUriBuilder.queryParam(Constants.Query.CUSTOMER_ID, banCustomerRequest.customerId())
                .build()
                .toUri();

        FraudRequest request = FraudRequest.builder()
                .customerId(banCustomerRequest.customerId())
                .build();

        FraudResponse fraudResponse = restTemplate.postForObject(
                uri,
                request,
                FraudResponse.class);

        log.debug("Gor response: {}", fraudResponse);
        return fraudResponse;
    }

    @Override
    public String test(String firstArg, String secondArg) {
        return firstArg + secondArg;
    }
}
