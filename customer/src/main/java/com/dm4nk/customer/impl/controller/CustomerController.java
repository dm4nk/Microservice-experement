package com.dm4nk.customer.impl.controller;

import com.dm4nk.customer.api.service.CustomerService;
import com.dm4nk.shared.request.customer.BanCustomerRequest;
import com.dm4nk.shared.request.customer.CustomerRegistrationRequest;
import com.dm4nk.shared.response.customer.CustomerRegistrationResponse;
import com.dm4nk.shared.response.fraud.FraudResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public CustomerRegistrationResponse registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("new customer registration {}", customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);

        return CustomerRegistrationResponse.builder()
                .message("Registered Customer " + customerRegistrationRequest.firstName())
                .build();
    }

    @PostMapping("/ban")
    public FraudResponse markAsFraudster(@RequestBody BanCustomerRequest banCustomerRequest, @RequestParam String customerId) {
        //todo remove or add validations to {customerId}
        log.info("marked as fraudster {}", banCustomerRequest.customerId());
        return customerService.banCustomer(banCustomerRequest);
    }

    @GetMapping("/test")
    public String test() {
        return customerService.test("foo", "bar");
    }
}
