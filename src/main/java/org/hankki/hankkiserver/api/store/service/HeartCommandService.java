package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.store.service.command.StoreDeleteCommand;
import org.hankki.hankkiserver.api.store.service.command.StorePostCommand;
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
@Transactional
public class HeartCommandService {

    private final HeartUpdater heartUpdater;
    private final HeartFinder heartFinder;
    private final HeartDeleter heartDeleter;
    private final UserFinder userFinder;
    private final StoreFinder storeFinder;

    public HeartCreateResponse createHeart(final StorePostCommand storePostCommand) {
        User user = userFinder.getUserReference(storePostCommand.userId());
        Store store = storeFinder.getStoreReference(storePostCommand.storeId());
        validateStoreHeartCreation(user, store);
        saveStoreHeart(user, store);
        increaseStoreHeartCount(store);
        return HeartCreateResponse.of(store.getId(), true, findTotalHeartCount(store));
    }

    public HeartDeleteResponse deleteHeart(final StoreDeleteCommand storeDeleteCommand) {
        User user = userFinder.getUserReference(storeDeleteCommand.userId());
        Store store = storeFinder.getStoreReference(storeDeleteCommand.storeId());
        validateStoreHeartRemoval(user, store);
        heartDeleter.deleteHeart(user,store);
        decreaseStoreHeartCount(store);
        return HeartDeleteResponse.of(store.getId(), false, findTotalHeartCount(store));
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

    private void increaseStoreHeartCount(final Store store) {
        store.increaseHeartCount();
    }

    private void decreaseStoreHeartCount(final Store store) {
        store.decreaseHeartCount();
    }

    private int findTotalHeartCount(final Store store) {
        return store.getHeartCount();
    }
}
