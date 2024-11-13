package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
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

    public void deleteStore(final Long id) {
        Store store =  storeRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(StoreErrorCode.STORE_NOT_FOUND));
        store.softDelete();
    }
}
