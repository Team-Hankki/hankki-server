package org.hankki.hankkiserver.common.exception;

import org.hankki.hankkiserver.common.code.AuthErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super(AuthErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(AuthErrorCode authErrorCode) {
        super(authErrorCode);
    }
}
