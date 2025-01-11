package org.hankki.hankkiserver.common.aspect;

import jakarta.persistence.OptimisticLockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hankki.hankkiserver.api.common.annotation.Retry;
import org.hankki.hankkiserver.common.code.HeartErrorCode;
import org.hankki.hankkiserver.common.exception.ConflictException;
import org.hibernate.StaleObjectStateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retryOptimisticLock(final ProceedingJoinPoint joinPoint, final Retry retry) throws Throwable {
        for (int attempt = 0; attempt < retry.maxAttempts(); attempt++) {
            try {
                return joinPoint.proceed();
            } catch (OptimisticLockException | ObjectOptimisticLockingFailureException | StaleObjectStateException e) {
                Thread.sleep(retry.backoff());
            }
        }
        throw new ConflictException(HeartErrorCode.HEART_COUNT_CONCURRENCY_ERROR);
    }
}
