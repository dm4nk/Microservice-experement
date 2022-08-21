package com.dm4nk.shared.response.fraud;

import lombok.Builder;

@Builder
public record FraudCheckResponse(Boolean isFraudster) {
}
