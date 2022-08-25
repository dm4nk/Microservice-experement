package com.dm4nk.customer.api.exceptions;

import com.dm4nk.customer.impl.util.enums.ExceptionTypes;

public interface ExceptionFactory {
    RuntimeException generateException(ExceptionTypes exceptionType, Object... args);
}
