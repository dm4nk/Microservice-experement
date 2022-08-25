package com.dm4nk.customer.impl.util.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Level {
    INFO("INFO"),
    DEBUG("DEBUG"),
    TRACE("TRACE");

    private final String value;

    public static Optional<Level> getEnumByValue(String value) {
        return Optional.of(Level.valueOf(value));
    }
}
