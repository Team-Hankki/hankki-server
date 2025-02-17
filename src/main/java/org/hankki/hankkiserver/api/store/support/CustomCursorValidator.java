package org.hankki.hankkiserver.api.store.support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.hankki.hankkiserver.api.store.service.response.CustomCursor;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomCursorValidator implements ConstraintValidator<CustomCursorValidation, CustomCursor> {

    @Override
    public boolean isValid(CustomCursor cursor, ConstraintValidatorContext context) {
        if (isFirstRequest(cursor)) {
            return true;
        }

        SortOption sortOption = getSortOptionFromRequest();
        if (sortOption == null) {
            return cursor.nextHeartCount() == null && cursor.nextLowestPrice() == null;
        }

        boolean isValid = switch(sortOption)  {
            case RECOMMENDED -> (cursor.nextHeartCount() != null && cursor.nextId() != null);
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

    private boolean isFirstRequest(CustomCursor cursor) {
        return (cursor.nextHeartCount() == null && cursor.nextLowestPrice() == null && cursor.nextId() == null);
    }
}
