package org.hankki.hankkiserver.common.exception;

import org.hankki.hankkiserver.common.code.AuthErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException() {
        super(AuthErrorCode.BAD_REQUEST);
    }

    public InvalidValueException(AuthErrorCode authErrorCode) {
        super(authErrorCode);
    }
}

