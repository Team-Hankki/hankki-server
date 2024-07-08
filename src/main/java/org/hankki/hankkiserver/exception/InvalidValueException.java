package org.hankki.hankkiserver.exception;

import org.hankki.hankkiserver.common.code.ErrorMessage;

public class InvalidValueException extends BusinessException {
    public InvalidValueException() {
        super(ErrorMessage.BAD_REQUEST);
    }

    public InvalidValueException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}

