package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreUpdater {

    private final StoreRepository storeRepository;

    public Store save(final Store store) {
        return storeRepository.save(store);
    }
}
