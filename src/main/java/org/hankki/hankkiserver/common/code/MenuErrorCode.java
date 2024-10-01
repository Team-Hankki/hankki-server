package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {

    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다."),
    ALREADY_EXISTED_MENU(HttpStatus.CONFLICT, "이미 존재하는 메뉴입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
