package com.exercide.ata.backend.service.exception;

import com.exercide.ata.backend.service.constant.ResponseMessageEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends Throwable {
    private final HttpStatus httpStatus;
    private final String code;
    private final String desc;

    public BusinessException(ResponseMessageEnum responseMessageEnum, String errorValue) {
        this.httpStatus = responseMessageEnum.getStatus();
        this.code = responseMessageEnum.getCode();
        this.desc = String.format(responseMessageEnum.getMessage(), errorValue);
    }
}
