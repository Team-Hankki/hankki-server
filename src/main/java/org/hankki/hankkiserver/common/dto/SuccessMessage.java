package org.hankki.hankkiserver.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage {

    OK(HttpStatus.OK, "요청이 성공했습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 성공했습니다."),
    CREATED(HttpStatus.CREATED, "요청이 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
