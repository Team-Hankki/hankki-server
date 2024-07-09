package org.hankki.hankkiserver.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.code.UserErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorCode> handleBadRequestException(BadRequestException e) {
        log.error("handleBadRequestException() in GlobalExceptionHandler throw BadRequestException : {}", e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorCode> handleUnauthorizedException(UnauthorizedException e) {
        log.error("handleUnauthorizedException() in GlobalExceptionHandler throw UnauthorizedException : {}", e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorCode> handleEntityNotFoundException(NotFoundException e) {
        log.error("handleEntityNotFoundException() in GlobalExceptionHandler throw EntityNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessErrorCode> handleException(Exception e) {
        log.error("handleException() in GlobalExceptionHandler throw Exception : {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BusinessErrorCode.INTERNAL_SERVER_ERROR);

    }
}
