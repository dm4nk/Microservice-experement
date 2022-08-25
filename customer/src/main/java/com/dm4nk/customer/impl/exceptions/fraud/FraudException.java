package com.dm4nk.customer.impl.exceptions.fraud;

public class FraudException extends IllegalStateException {
    public FraudException() {
    }

    public FraudException(String s) {
        super(s);
    }
}
