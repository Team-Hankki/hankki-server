package org.hankki.hankkiserver.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    // reuqest error
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_PLATFORM_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 플랫폼입니다."),
    UNSUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "키 생성에 사용된 알고리즘을 지원하지 않습니다: "),
    INVALID_KEY_SPEC(HttpStatus.BAD_REQUEST, "공개 키 생성에 잘못된 키 사양이 제공되었습니다."),

    // unauthorized error
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 형식이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    MISMATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지 않습니다."),
    INVALID_KAKAO_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 카카오 엑세스 토큰입니다."),
    INVALID_APPLE_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 아이덴티티 토큰입니다."),
    EXPIRED_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "아이덴티티 토큰이 만료되었습니다."),
    INVALID_IDENTITY_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "애플 아이덴티티 토큰의 값이 올바르지 않습니다."),

    // not found error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 회원입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다."),
    USER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레쉬 토큰을 찾을 수 없습니다."),

    // internal server error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),

    // method not allowed error
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
