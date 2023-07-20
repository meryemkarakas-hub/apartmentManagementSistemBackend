package com.managementSystem.apartmentManagementSystem.core.exception;

import org.springframework.http.HttpStatus;

//@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid business rules")
public class BusinessRuleException
        extends RuntimeException {
    private final String reason;

    public BusinessRuleException(String reason, Throwable err) {
        super(err);
        this.reason = reason;
    }

    public BusinessRuleException(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return reason;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_ACCEPTABLE;
    }
}
