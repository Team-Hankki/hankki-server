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
        Long userId = storePostCommand.userId();
        Long storeId = storePostCommand.storeId();
        heartFinder.findByUserIdAndStoreId(userId, storeId)
                .ifPresent(heart -> {
                    throw new ConflictException(HeartErrorCode.ALREADY_EXISTED_HEART);
                });
        Store store = storeFinder.getStore(storeId);
        heartUpdater.saveHeart(Heart.createHeart(userFinder.getUserReference(userId), store));
        store.updateHearCount(false);
        return HeartCreateResponse.of(storeId, true);
    }

    public HeartDeleteResponse deleteHeart(final StoreDeleteCommand storeDeleteCommand) {
        Long userId = storeDeleteCommand.userId();
        Long storeId = storeDeleteCommand.storeId();

        heartFinder.findByUserIdAndStoreId(userId, storeId)
                .orElseThrow(() -> new ConflictException(HeartErrorCode.ALREADY_DELETED_HEART));
        heartDeleter.deleteHeart(userId,storeId);
        storeFinder.getStore(storeId).updateHearCount(true);
        return HeartDeleteResponse.of(storeId, false);
    }
}
