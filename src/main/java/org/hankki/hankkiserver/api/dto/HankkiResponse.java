package org.hankki.hankkiserver.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hankki.hankkiserver.common.code.ErrorCode;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HankkiResponse<T> {

    private final int status;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final T data;

    public static HankkiResponse<?> of(CommonSuccessCode commonSuccessCode) {
        return builder()
                .status(commonSuccessCode.getHttpStatus().value())
                .message(commonSuccessCode.getMessage())
                .build();
    }

    public static <T> HankkiResponse<?> of(CommonSuccessCode commonSuccessCode, T data) {
        return builder()
                .status(commonSuccessCode.getHttpStatus().value())
                .message(commonSuccessCode.getMessage())
                .data(data)
                .build();
    }

    public static HankkiResponse<?> of(ErrorCode errorCode) {
        return builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }
}
