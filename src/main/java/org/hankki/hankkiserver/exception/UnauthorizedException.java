package org.hankki.hankkiserver.exception;

import org.hankki.hankkiserver.common.dto.ErrorMessage;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(ErrorMessage.UNAUTHORIZED);
    }

    public UnauthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}

