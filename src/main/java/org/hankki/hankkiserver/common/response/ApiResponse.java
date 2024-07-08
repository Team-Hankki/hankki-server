package org.hankki.hankkiserver.common.response;

import org.hankki.hankkiserver.common.response.code.ErrorCode;
import org.hankki.hankkiserver.common.response.code.SuccessCode;
import org.springframework.http.ResponseEntity;

public interface ApiResponse {

    static ResponseEntity<BaseResponse<?>> success(SuccessCode successCode) {
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(BaseResponse.of(successCode));
    }

    static <T> ResponseEntity<BaseResponse<?>> success(SuccessCode successCode, T data) {
        return org.springframework.http.ResponseEntity.status(successCode.getHttpStatus())
                .body(BaseResponse.of(successCode, data));
    }

    static ResponseEntity<BaseResponse<?>> failure(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(BaseResponse.of(errorCode));
    }
}
