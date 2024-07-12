package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.store.service.command.StoreDeleteCommand;
import org.hankki.hankkiserver.api.store.service.command.StorePostCommand;
import org.hankki.hankkiserver.api.store.service.response.HeartCreateResponse;
import org.hankki.hankkiserver.common.code.HeartErrorCode;
import org.hankki.hankkiserver.common.exception.ConflictException;
import org.hankki.hankkiserver.domain.heart.model.Heart;
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
        validateCreateStoreHeart(userId, storeId);
        saveStoreHeart(userId, storeId);
        updateStoreHeartCount(storeId, false);
        return HeartCreateResponse.of(storeId, true);
    }

    public void deleteHeart(final StoreDeleteCommand storeDeleteCommand) {
        Long userId = storeDeleteCommand.userId();
        Long storeId = storeDeleteCommand.storeId();
        validateDeleteStoreHeart(userId, storeId);
        heartDeleter.deleteHeart(userId,storeId);
        updateStoreHeartCount(storeId, true);
    }

    private void validateCreateStoreHeart(final Long userId, final Long storeId) {
        heartFinder.findByUserIdAndStoreId(userId, storeId)
                .ifPresent(heart -> {
                    throw new ConflictException(HeartErrorCode.ALREADY_EXISTED_HEART);
                });
    }

    private void validateDeleteStoreHeart(final Long userId, final Long storeId) {
        heartFinder.findByUserIdAndStoreId(userId, storeId)
                .orElseThrow(() -> new ConflictException(HeartErrorCode.ALREADY_DELETED_HEART));
    }

    private void saveStoreHeart(final Long userId, final Long storeId) {
        Heart heart = Heart.createHeart(userFinder.getUserReference(userId), storeFinder.getStoreReference(storeId));
        heartUpdater.saveHeart(heart);
    }

    private void updateStoreHeartCount(final Long storeId, final boolean isDeleted) {
        storeFinder.getStore(storeId).updateHearCount(isDeleted);
    }
}
