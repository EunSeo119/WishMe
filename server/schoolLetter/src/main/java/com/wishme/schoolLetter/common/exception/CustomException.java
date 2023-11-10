package com.wishme.schoolLetter.common.exception;

import com.wishme.schoolLetter.common.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
