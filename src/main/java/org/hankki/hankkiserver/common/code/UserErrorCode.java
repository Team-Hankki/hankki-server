package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 회원입니다."),
    USER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
