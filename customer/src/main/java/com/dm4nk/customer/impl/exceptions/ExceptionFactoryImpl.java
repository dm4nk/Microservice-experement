package com.dm4nk.customer.impl.exceptions;

import com.dm4nk.customer.impl.util.enums.ExceptionTypes;
import com.dm4nk.customer.impl.exceptions.fraud.FraudException;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Component
public class ExceptionFactoryImpl implements com.dm4nk.customer.api.exceptions.ExceptionFactory {
    private static final ImmutableMap<ExceptionTypes, Class<? extends RuntimeException>> TYPE_TO_EXCEPTION = ImmutableMap.of(
            ExceptionTypes.FRAUD, FraudException.class
    );

    private static final ImmutableMap<ExceptionTypes, String> TYPE_TO_MESSAGE = ImmutableMap.of(
            ExceptionTypes.FRAUD, "Customer with Id: %s is Fraudster"
    );

    @Override
    @SuppressWarnings("ConstantConditions")
    public RuntimeException generateException(ExceptionTypes exceptionType, Object... args) {
        try {
            return TYPE_TO_EXCEPTION.getOrDefault(exceptionType, RuntimeException.class)
                    .getDeclaredConstructor(String.class)
                    .newInstance(String.format(
                            TYPE_TO_MESSAGE.getOrDefault(exceptionType, "%s"),
                            args
                    ));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException("Could not generate exception, check number and types of args", e);
        }
    }
}
