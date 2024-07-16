package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.heart.repository.HeartRepository;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HeartFinder {

    private final HeartRepository heartRepository;

    public boolean existsByUserAndStore(final User user, final Store store) {
        return heartRepository.existsByUserAndStore(user, store);
    }

    public List<Heart> findHeartedStoresByUserId(final Long userId) {
        return heartRepository.findAllWithStoreByUserId(userId);
    }
}
