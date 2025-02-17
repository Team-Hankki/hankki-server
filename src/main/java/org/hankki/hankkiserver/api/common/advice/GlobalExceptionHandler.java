package org.hankki.hankkiserver.api.common.advice;

import jakarta.validation.ConstraintViolationException;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.code.StoreImageErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.common.exception.ConflictException;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkClientException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public HankkiResponse<Void> handleBadRequestException(BadRequestException e) {
        log.warn("handleBadRequestException() in GlobalExceptionHandler throw BadRequestException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public HankkiResponse<Void> handleUnauthorizedException(UnauthorizedException e) {
        log.warn("handleUnauthorizedException() in GlobalExceptionHandler throw UnauthorizedException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public HankkiResponse<Void> handleEntityNotFoundException(NotFoundException e) {
        log.warn("handleEntityNotFoundException() in GlobalExceptionHandler throw EntityNotFoundException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(ConflictException.class)
    public HankkiResponse<Void> handleConflictException(ConflictException e) {
        log.warn("handleConflictException() in GlobalExceptionHandler throw ConflictException : {}", e.getMessage());
        return HankkiResponse.fail(e.getErrorCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HankkiResponse<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("handleMissingServletRequestParameterException() in GlobalExceptionHandler throw MissingServletRequestParameterException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public HankkiResponse<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("handleMaxUploadSizeExceededException() in GlobalExceptionHandler throw MaxUploadSizeExceededException : {}", e.getMessage());
        return HankkiResponse.fail(StoreErrorCode.STORE_FILE_SIZE_EXCEEDED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HankkiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public HankkiResponse<Void> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        log.warn("handleHandlerMethodValidationException() in GlobalExceptionHandler throw HandlerMethodValidationException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST.getHttpStatus(), getDefaultMessage(e));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HankkiResponse<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("handleMethodArgumentTypeMismatchException() in GlobalExceptionHandler throw MethodArgumentTypeMismatchException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST.getHttpStatus(), getRootCauseMessage(e));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HankkiResponse<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("handleHttpRequestMethodNotSupportedException() in GlobalExceptionHandler throw HttpRequestMethodNotSupportedException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public HankkiResponse<Void> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("handleNoResourceFoundException() in GlobalExceptionHandler throw NoResourceFoundException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HankkiResponse<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("handleHttpMessageNotReadableException() in GlobalExceptionHandler throw HttpMessageNotReadableException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(SdkClientException.class)
    public HankkiResponse<Void> handleSdkClientException(SdkClientException e) {
        log.warn("handleSdkClientException() in GlobalExceptionHandler throw SdkClientException : {}", e.getMessage());
        return HankkiResponse.fail(StoreImageErrorCode.STORE_IMAGE_UPLOAD_FAILED);
    }

    @ExceptionHandler(Exception.class)
    public HankkiResponse<Void> handleException(Exception e) {
        log.error("[500] INTERNAL SERVER ERROR({}) : {}",e.getClass() , e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public HankkiResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("handleConstraintViolationException() in GlobalExceptionHandler throw ConstraintViolationException : {}", e.getMessage());
        return HankkiResponse.fail(BusinessErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.class)
    public HankkiResponse<Void> handleFeignException(FeignException e) {
        log.warn("handleFeignException() in GlobalExceptionHandler throw FeignException : {}", e.getMessage());
        return HankkiResponse.fail(AuthErrorCode.INTERNAL_SERVER_ERROR);
    }

    private String getRootCauseMessage(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }

    private String getDefaultMessage(HandlerMethodValidationException e) {
        return e.getAllValidationResults().get(0).getResolvableErrors().get(0).getDefaultMessage();
    }
}
