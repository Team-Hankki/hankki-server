package org.hankki.hankkiserver.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public HankkiResponse<Void> handleBadRequestException(BadRequestException e) {
        log.error("handleBadRequestException() in GlobalExceptionHandler throw BadRequestException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public HankkiResponse<Void> handleUnauthorizedException(UnauthorizedException e) {
        log.error("handleUnauthorizedException() in GlobalExceptionHandler throw UnauthorizedException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public HankkiResponse<Void> handleEntityNotFoundException(NotFoundException e) {
        log.error("handleEntityNotFoundException() in GlobalExceptionHandler throw EntityNotFoundException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public HankkiResponse<Void> handleException(Exception e) {
        log.error("handleException() in GlobalExceptionHandler throw Exception : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.INTERNAL_SERVER_ERROR);

    }
}