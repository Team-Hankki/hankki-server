package org.hankki.hankkiserver.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
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

    public static BaseResponse<?> of(CommonSuccessCode commonSuccessCode) {
        return builder()
                .status(commonSuccessCode.getHttpStatus().value())
                .message(commonSuccessCode.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(CommonSuccessCode commonSuccessCode, T data) {
        return builder()
                .status(commonSuccessCode.getHttpStatus().value())
                .message(commonSuccessCode.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(AuthErrorCode authErrorCode) {
        return builder()
                .status(authErrorCode.getHttpStatus().value())
                .message(authErrorCode.getMessage())
                .build();
    }
}
