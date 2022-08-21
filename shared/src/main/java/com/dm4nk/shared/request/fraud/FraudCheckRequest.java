package com.dm4nk.shared.request.fraud;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FraudCheckRequest(UUID customerId) {
}
