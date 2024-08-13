package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreImageErrorCode implements ErrorCode {

    STORE_IMAGE_UPLOAD_FAILED(HttpStatus.BAD_GATEWAY, "외부 자징소에 이미지 업로드에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
