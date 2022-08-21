package com.dm4nk.fraud.service;

import com.dm4nk.fraud.model.FraudCheckHistory;
import com.dm4nk.fraud.repository.FraudCheckHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public record FraudService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {

    public void markAsFraudster(UUID customerId) {
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(Boolean.TRUE)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    public Boolean isFraudCustomer(UUID customerId) {
        return !fraudCheckHistoryRepository.countAllByIsFraudsterTrueAndCustomerId(customerId).equals(0);
    }
}
