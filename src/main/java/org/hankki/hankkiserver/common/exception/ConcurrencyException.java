package org.hankki.hankkiserver.common.exception;

import lombok.Getter;
import org.hankki.hankkiserver.common.code.ErrorCode;

@Getter
public class ConcurrencyException extends RuntimeException {
    private final ErrorCode errorCode;

    public ConcurrencyException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
