package org.hankki.hankkiserver.api.store.support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.micrometer.observation.annotation.Observed;

public class CustomCursorValidator implements ConstraintValidator<CustomCursorValidation, CustomCursor> {

    @Override
    public void initialize(CustomCursorValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(CustomCursor cursor, ConstraintValidatorContext context) {
        if (cursor == null) {
            return true;
        }

        SortOption sortOption = getSortOptionFromRequest();
        if (sortOption == null) {
            return true;
        }

        boolean isValid = switch(sortOption)  {
            case RECOMMENDED -> cursor.nextHeartCount() != null && cursor.nextId() != null;
            case LOWESTPRICE -> cursor.nextLowestPrice() != null && cursor.nextId() != null;
            case LATEST -> cursor.nextHeartCount() == null && cursor.nextLowestPrice() == null;
        };

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(StoreErrorCode.BAD_CURSOR_SET.getMessage())
                    .addConstraintViolation();
        }
        return isValid;
    }

    private SortOption getSortOptionFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String sortOption = request.getParameter("sortOption");
            return sortOption != null ? SortOption.valueOf(sortOption.toUpperCase()) : null;
        }
        return null;
    }
}
