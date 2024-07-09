package org.hankki.hankkiserver.common.exception;

import org.hankki.hankkiserver.common.code.ErrorCode;

public class InternalServerException extends RuntimeException {
    private final ErrorCode errorCode;

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
