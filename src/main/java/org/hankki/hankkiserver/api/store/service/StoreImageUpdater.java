package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.store.model.StoreImage;
import org.hankki.hankkiserver.domain.store.repository.StoreImageRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreImageUpdater {

    private final StoreImageRepository storeImageRepository;

    public void saveImage(StoreImage image) {
        storeImageRepository.save(image);
    }
}
