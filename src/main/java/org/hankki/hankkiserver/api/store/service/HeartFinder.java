package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.heart.repository.HeartRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HeartFinder {

    private final HeartRepository heartRepository;

    public Optional<Heart> findByUserIdAndStoreId(Long userId, Long storeId) {
        return heartRepository.findByUserIdAndStoreId(userId, storeId);
    }
}
