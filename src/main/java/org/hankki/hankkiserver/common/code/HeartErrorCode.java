package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HeartErrorCode implements ErrorCode {

    ALREADY_EXISTED_HEART(HttpStatus.CONFLICT, "이미 좋아요 한 가게입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
