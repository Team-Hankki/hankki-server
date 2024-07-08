package org.hankki.hankkiserver.exception;

import org.hankki.hankkiserver.common.code.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
