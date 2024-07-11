package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.heart.repository.HeartRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartDeleter {

    private final HeartRepository heartRepository;

    public void deleteHeart(final Long userId, final Long storeId) {
        heartRepository.deleteByUserIdAndStoreId(userId, storeId);
    }
}
