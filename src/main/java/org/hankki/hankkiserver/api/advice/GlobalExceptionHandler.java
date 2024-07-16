package org.hankki.hankkiserver.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.common.exception.ConflictException;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
<<<<<<< HEAD
=======

import org.springframework.web.bind.MethodArgumentNotValidException;
>>>>>>> 9dbf784 ([feat] add exceptions in GlobalExceptionHandler.java)
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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

    @ExceptionHandler(ConflictException.class)
    public HankkiResponse<Void> handleConflictException(ConflictException e) {
        log.error("handleConflictException() in GlobalExceptionHandler throw ConflictException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HankkiResponse<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handleMissingServletRequestParameterException() in GlobalExceptionHandler throw MissingServletRequestParameterException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public HankkiResponse<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("handleMaxUploadSizeExceededException() in GlobalExceptionHandler throw MaxUploadSizeExceededException : {}", e.getMessage());
        return HankkiResponse.fail(StoreErrorCode.STORE_FILE_SIZE_EXCEEDED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HankkiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public HankkiResponse<Void> handleException(Exception e) {
        log.error("handleException() in GlobalExceptionHandler throw Exception [{}] : {}", e.getClass() , e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.INTERNAL_SERVER_ERROR);

    }
}
