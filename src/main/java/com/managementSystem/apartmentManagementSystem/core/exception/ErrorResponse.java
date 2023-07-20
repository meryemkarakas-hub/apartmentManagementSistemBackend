package com.managementSystem.apartmentManagementSystem.core.exception;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.StyledEditorKit;

@Getter
@Setter
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}