package com.dm4nk.fraud.repository;

import com.dm4nk.fraud.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory, UUID> {
    Integer countAllByIsFraudsterTrueAndCustomerId(UUID customerId);
}
