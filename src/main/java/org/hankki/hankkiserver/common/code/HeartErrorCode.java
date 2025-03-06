package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HeartErrorCode implements ErrorCode {

    ALREADY_EXISTED_HEART(HttpStatus.CONFLICT, "이미 좋아요를 누른 식당이에요"),
    ALREADY_NO_HEART(HttpStatus.CONFLICT, "이미 좋아요를 취소한 식당이에요"),
    HEART_COUNT_CONCURRENCY_ERROR(HttpStatus.CONFLICT, "오류가 발생했어요" + System.lineSeparator() + "좋아요를 다시 눌러주세요");

    private final HttpStatus httpStatus;
    private final String message;
}

