package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HeartErrorCode implements ErrorCode {

    ALREADY_EXISTED_HEART(HttpStatus.CONFLICT, "이미 좋아요 한 가게입니다."),
    ALREADY_NO_HEART(HttpStatus.CONFLICT, "이미 좋아요를 취소한 가게입니다."),
    HEART_COUNT_CONCURRENCY_ERROR(HttpStatus.CONFLICT, "좋아요 처리 중 충돌이 발생했습니다. 잠시 후 다시 시도해주세요."),
    INVALID_HEART_COUNT(HttpStatus.BAD_REQUEST, "좋아요 개수는 음수가 될 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
