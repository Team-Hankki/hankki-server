package org.hankki.hankkiserver.api.dto;

import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.http.ResponseEntity;

public interface ApiResponse {

    static ResponseEntity<BaseResponse<?>> success(CommonSuccessCode commonSuccessCode) {
        return ResponseEntity.status(commonSuccessCode.getHttpStatus())
                .body(BaseResponse.of(commonSuccessCode));
    }

    static <T> ResponseEntity<BaseResponse<?>> success(CommonSuccessCode commonSuccessCode, T data) {
        return org.springframework.http.ResponseEntity.status(commonSuccessCode.getHttpStatus())
                .body(BaseResponse.of(commonSuccessCode, data));
    }

    static ResponseEntity<BaseResponse<?>> failure(AuthErrorCode authErrorCode) {
        return ResponseEntity.status(authErrorCode.getHttpStatus())
                .body(BaseResponse.of(authErrorCode));
    }
}
