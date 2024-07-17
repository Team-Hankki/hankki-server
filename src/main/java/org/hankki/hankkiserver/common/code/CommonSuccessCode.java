package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonSuccessCode implements SuccessCode {

    OK(HttpStatus.OK, "요청이 성공했습니다(200)."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 성공했습니다(204)."),
    CREATED(HttpStatus.CREATED, "리소스가 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
