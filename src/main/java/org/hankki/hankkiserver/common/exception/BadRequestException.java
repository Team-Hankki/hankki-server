package org.hankki.hankkiserver.common.exception;

import lombok.Getter;
import org.hankki.hankkiserver.common.code.ErrorCode;

@Getter
public class BadRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}