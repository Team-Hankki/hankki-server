package org.hankki.hankkiserver.api.dto;

import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.http.ResponseEntity;

public interface ApiResponse {

    static ResponseEntity<HankkiResponse<?>> success(CommonSuccessCode commonSuccessCode) {
        return ResponseEntity.status(commonSuccessCode.getHttpStatus())
                .body(HankkiResponse.of(commonSuccessCode));
    }

    static <T> ResponseEntity<HankkiResponse<?>> success(CommonSuccessCode commonSuccessCode, T data) {
        return org.springframework.http.ResponseEntity.status(commonSuccessCode.getHttpStatus())
                .body(HankkiResponse.of(commonSuccessCode, data));
    }

    static ResponseEntity<HankkiResponse<?>> failure(AuthErrorCode authErrorCode) {
        return ResponseEntity.status(authErrorCode.getHttpStatus())
                .body(HankkiResponse.of(authErrorCode));
    }
}
