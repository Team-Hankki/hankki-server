package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {

    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다."),
    STORE_FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 크기가 초과되었습니다."),
    BAD_STORE_INFO(HttpStatus.BAD_REQUEST, "잘못된 식당 정보입니다."),
    BAD_CURSOR_SET(HttpStatus.BAD_REQUEST, "cursor 값이 SortOption에 맞지 않습니다."),
    INVALID_PRICE_CATEGORY(HttpStatus.BAD_REQUEST, "price category가 맞지 않습니다."),
    INVALID_SORT_OPTION(HttpStatus.BAD_REQUEST, "정렬 옵션이 맞지 않습니다."),
    INVALID_STORE_CATEGORY(HttpStatus.BAD_REQUEST, "store category가 맞지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
