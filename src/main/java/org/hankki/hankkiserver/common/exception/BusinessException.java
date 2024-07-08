package org.hankki.hankkiserver.common.exception;

import org.hankki.hankkiserver.common.code.AuthErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final AuthErrorCode authErrorCode;

    public BusinessException(AuthErrorCode authErrorCode) {
        super(authErrorCode.getMessage());
        this.authErrorCode = authErrorCode;
    }
}
