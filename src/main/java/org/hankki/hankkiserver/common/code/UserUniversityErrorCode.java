package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserUniversityErrorCode implements ErrorCode {
    USER_UNIVERSITY_NOT_FOUND(HttpStatus.NOT_FOUND, "대학 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
