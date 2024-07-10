package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandService {

    private final HeartUpdater heartUpdater;
    private final UserFinder userFinder;
    private final StoreFinder storeFinder;

    public void createHeart(Long storeId, Long userId) {
        heartUpdater.saveHeart(Heart.createHeart(userFinder.getUser(userId), storeFinder.getStore(storeId)));
    }
}
