package com.exercide.ata.backend.service.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    private String statusCode;
    private String message;
    private String description;
}
