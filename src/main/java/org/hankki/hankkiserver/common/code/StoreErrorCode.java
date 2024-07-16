package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {

    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."),
    STORE_ALREADY_REGISTERED(HttpStatus.CONFLICT, "이미 등록된 가게입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
