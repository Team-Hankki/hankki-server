package org.hankki.hankkiserver.common.exception;

import lombok.Getter;
import org.hankki.hankkiserver.common.code.ErrorCode;

@Getter
public class ConflictException extends RuntimeException {
    private final ErrorCode errorCode;
    private Object data;

    public ConflictException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ConflictException(ErrorCode errorCode, Object data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = data;
    }
}
