package org.hankki.hankkiserver.exception;

import org.hankki.hankkiserver.common.dto.ErrorMessage;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super(ErrorMessage.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
