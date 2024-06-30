package org.hankki.hankkiserver.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.common.dto.SuccessMessage;
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

    public static BaseResponse<?> of(SuccessMessage successMessage) {
        return builder()
                .status(successMessage.getHttpStatus().value())
                .message(successMessage.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(SuccessMessage successMessage, T data) {
        return builder()
                .status(successMessage.getHttpStatus().value())
                .message(successMessage.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(ErrorMessage errorMessage) {
        return builder()
                .status(errorMessage.getHttpStatus().value())
                .message(errorMessage.getMessage())
                .build();
    }
}
