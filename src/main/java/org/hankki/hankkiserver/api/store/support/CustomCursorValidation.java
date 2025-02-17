package org.hankki.hankkiserver.api.store.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = CustomCursorValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCursorValidation {
    String message() default "cursor 값이 SortOption에 맞지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
