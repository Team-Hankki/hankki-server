package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.user.service.UserFinder;
import org.hankki.hankkiserver.api.common.annotation.Retry;
import org.hankki.hankkiserver.api.store.service.command.HeartDeleteCommand;
import org.hankki.hankkiserver.api.store.service.command.HeartPostCommand;
import org.hankki.hankkiserver.api.store.service.response.HeartCreateResponse;
import org.hankki.hankkiserver.api.store.service.response.HeartDeleteResponse;
import org.hankki.hankkiserver.common.code.HeartErrorCode;
import org.hankki.hankkiserver.common.exception.ConflictException;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.user.model.User;
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

    @Retry
    @Transactional
    public HeartCreateResponse createHeart(final HeartPostCommand heartPostCommand) {
        User user = userFinder.getUserReference(heartPostCommand.userId());
        Store store = storeFinder.findByIdWhereDeletedIsFalse(heartPostCommand.storeId());
        validateStoreHeartCreation(user, store);
        saveStoreHeart(user, store);
        store.increaseHeartCount();
        return HeartCreateResponse.of(store);
    }

    @Retry
    @Transactional
    public HeartDeleteResponse deleteHeart(final HeartDeleteCommand heartDeleteCommand) {
        User user = userFinder.getUserReference(heartDeleteCommand.userId());
        Store store = storeFinder.findByIdWhereDeletedIsFalse(heartDeleteCommand.storeId());
        validateStoreHeartRemoval(user, store);
        heartDeleter.deleteHeart(user,store);
        store.decreaseHeartCount();
        return HeartDeleteResponse.of(store);
    }

    private void validateStoreHeartCreation(final User user, final Store store) {
        if(heartFinder.existsByUserAndStore(user, store)){
            throw new ConflictException(HeartErrorCode.ALREADY_EXISTED_HEART);
        }
    }

    private void validateStoreHeartRemoval(final User user, final Store store) {
        if(!heartFinder.existsByUserAndStore(user, store)){
            throw new ConflictException(HeartErrorCode.ALREADY_NO_HEART);
        }
    }

    private void saveStoreHeart(final User user, final Store store) {
        heartUpdater.saveHeart(Heart.createHeart(user, store));
    }
}
