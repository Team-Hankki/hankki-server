package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.store.service.command.HeartCommand;
import org.hankki.hankkiserver.api.store.service.response.HeartCreateResponse;
import org.hankki.hankkiserver.api.store.service.response.HeartDeleteResponse;
import org.hankki.hankkiserver.common.code.HeartErrorCode;
import org.hankki.hankkiserver.common.exception.ConcurrencyException;
import org.hankki.hankkiserver.common.exception.ConflictException;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartCommandService {

    private final HeartUpdater heartUpdater;
    private final HeartFinder heartFinder;
    private final HeartDeleter heartDeleter;
    private final UserFinder userFinder;
    private final StoreFinder storeFinder;

    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            backoff = @Backoff(delay = 100))
    @Transactional
    public HeartCreateResponse createHeart(final HeartCommand heartCommand) {
        User user = userFinder.getUserReference(heartCommand.userId());
        Store store = storeFinder.findByIdWhereDeletedIsFalse(heartCommand.storeId());
        validateStoreHeartCreation(user, store);
        saveStoreHeart(user, store);
        store.increaseHeartCount();
        return HeartCreateResponse.of(store);
    }

    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            backoff = @Backoff(delay = 100))
    @Transactional
    public HeartDeleteResponse deleteHeart(final HeartCommand heartCommand) {
        User user = userFinder.getUserReference(heartCommand.userId());
        Store store = storeFinder.findByIdWhereDeletedIsFalse(heartCommand.storeId());
        validateStoreHeartRemoval(user, store);
        heartDeleter.deleteHeart(user, store);
        store.decreaseHeartCount();
        return HeartDeleteResponse.of(store);
    }

    @Recover
    public HeartCreateResponse recoverFromOptimisticLockFailure(final ObjectOptimisticLockingFailureException e,
                                                                final HeartCommand heartCommand) {
        throw new ConcurrencyException(HeartErrorCode.HEART_COUNT_CONCURRENCY_ERROR);
    }

    private void validateStoreHeartCreation(final User user, final Store store) {
        if (heartFinder.existsByUserAndStore(user, store)) {
            throw new ConflictException(HeartErrorCode.ALREADY_EXISTED_HEART);
        }
    }

    private void validateStoreHeartRemoval(final User user, final Store store) {
        if (!heartFinder.existsByUserAndStore(user, store)) {
            throw new ConflictException(HeartErrorCode.ALREADY_NO_HEART);
        }
    }

    private void saveStoreHeart(final User user, final Store store) {
        heartUpdater.saveHeart(Heart.createHeart(user, store));
    }
}
