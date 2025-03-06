package org.hankki.hankkiserver.api.dto;

import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.code.SuccessCode;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HankkiResponse<T> {

    private final int code;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> HankkiResponse<T> success(SuccessCode success) {
        return new HankkiResponse<>(success.getHttpStatus().value(), success.getMessage());
    }

    public static <T> HankkiResponse<T> success(SuccessCode success, T data) {
        return new HankkiResponse<>(success.getHttpStatus().value(), success.getMessage(), data);
    }

    public static <T> HankkiResponse<T> fail(ErrorCode error) {
        return new HankkiResponse<>(error.getHttpStatus().value(), error.getMessage());
    }

    public static <T> HankkiResponse<T> fail(HttpStatus status, String message) {
        return new HankkiResponse<>(status.value(), message);
    }
}
