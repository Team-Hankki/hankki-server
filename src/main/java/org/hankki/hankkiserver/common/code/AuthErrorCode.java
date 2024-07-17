package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    /**
     * 400 Bad Request
     **/
    INVALID_PLATFORM_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 플랫폼입니다."),
    UNSUPPORTED_ALGORITHM(HttpStatus.BAD_REQUEST, "키 생성에 사용된 알고리즘을 지원하지 않습니다: "),
    INVALID_KEY_SPEC(HttpStatus.BAD_REQUEST, "공개 키 생성에 잘못된 키 사양이 제공되었습니다."),
    FAILED_TO_LOAD_PRIVATE_KEY(HttpStatus.BAD_REQUEST, "개인 키를 로드하는 데 실패했습니다."),
    APPLE_REVOKE_FAILED(HttpStatus.BAD_REQUEST, "Apple 탈퇴에 실패했습니다."),

    /**
     * 401 Unauthorized
     **/
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    MISMATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지 않습니다."),
    INVALID_APPLE_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 아이덴티티 토큰입니다."),
    EXPIRED_IDENTITY_TOKEN(HttpStatus.UNAUTHORIZED, "아이덴티티 토큰이 만료되었습니다."),
    INVALID_IDENTITY_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "애플 아이덴티티 토큰의 값이 올바르지 않습니다."),
    MISSING_BEARER_PREFIX(HttpStatus.UNAUTHORIZED, "Bearer가 누락되었습니다."),

    /**
     * 500 Internal Server Error
     **/
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
