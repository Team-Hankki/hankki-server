package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.heart.repository.HeartRepository;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartDeleter {

    private final HeartRepository heartRepository;

    public void deleteHeart(final User user, final Store store) {
        heartRepository.deleteByUserAndStore(user, store);
    }

    public void deleteHeart(final Store store) {
        heartRepository.deleteHeartByStore(store);
    }
}
