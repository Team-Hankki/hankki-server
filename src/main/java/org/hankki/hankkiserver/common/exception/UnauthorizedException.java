package org.hankki.hankkiserver.common.exception;

import org.hankki.hankkiserver.common.code.AuthErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(AuthErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(AuthErrorCode authErrorCode) {
        super(authErrorCode);
    }
}

