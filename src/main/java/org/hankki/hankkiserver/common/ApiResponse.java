package org.hankki.hankkiserver.common;

import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.common.dto.SuccessMessage;
import org.springframework.http.ResponseEntity;

public interface ApiResponse {

    static ResponseEntity<BaseResponse<?>> success(SuccessMessage successMessage) {
        return ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage));
    }

    static <T> ResponseEntity<BaseResponse<?>> success(SuccessMessage successMessage, T data) {
        return org.springframework.http.ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage, data));
    }

    static ResponseEntity<BaseResponse<?>> failure(ErrorMessage errorMessage) {
        return ResponseEntity.status(errorMessage.getHttpStatus())
                .body(BaseResponse.of(errorMessage));
    }
}
