package com.dm4nk.fraud.controller;

import com.dm4nk.fraud.service.FraudService;
import com.dm4nk.shared.response.fraud.FraudCheckResponse;
import com.dm4nk.shared.response.fraud.FraudResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudController(FraudService fraudService) {

    @PostMapping(path = "/check")
    public FraudCheckResponse isFraudster(@RequestParam String customerId) {
        Boolean isFraudster = nonNull(customerId) && fraudService.isFraudCustomer(UUID.fromString(customerId));

        return FraudCheckResponse.builder()
                .isFraudster(isFraudster)
                .build();
    }

    @PostMapping(path = "/fraud")
    public FraudResponse markAsFraudster(@RequestParam String customerId) {
        fraudService.markAsFraudster(UUID.fromString(customerId));

        return FraudResponse.builder()
                .message("marked " + customerId + "as fraudster")
                .build();
    }
}
