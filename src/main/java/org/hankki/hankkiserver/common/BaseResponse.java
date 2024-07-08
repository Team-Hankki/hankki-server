package org.hankki.hankkiserver.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.code.SuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {

    private final int status;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final T data;

    public static BaseResponse<?> of(SuccessCode successCode) {
        return builder()
                .status(successCode.getHttpStatus().value())
                .message(successCode.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(SuccessCode successCode, T data) {
        return builder()
                .status(successCode.getHttpStatus().value())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(ErrorCode errorCode) {
        return builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }
}
