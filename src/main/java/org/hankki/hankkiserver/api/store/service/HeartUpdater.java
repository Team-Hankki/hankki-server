package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.heart.repository.HeartRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartUpdater {

    private final HeartRepository heartRepository;

    public void saveHeart(final Heart heart) {
        heartRepository.save(heart);
    }
}
