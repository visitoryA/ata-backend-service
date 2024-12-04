package com.exercide.ata.backend.service.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ResponseMessageEnum {
    SUCCESS(HttpStatus.OK, "AT2000", "Success"),
    MISSING_REQ(HttpStatus.BAD_REQUEST, "AT4001", "%s is missing"),
    INVALID_REQ(HttpStatus.BAD_REQUEST, "AT4002", "%s is invalid");

    @Getter
    private final HttpStatus status;
    @Getter
    private final String code;
    @Getter
    private final String message;

    ResponseMessageEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
