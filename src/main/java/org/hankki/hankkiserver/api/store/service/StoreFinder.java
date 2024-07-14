package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.StoreErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StoreFinder {

    private final StoreRepository storeRepository;

    public Store getStoreReference(final Long id) {
        return storeRepository.getReferenceById(id);
    }


    public Store findByIdWhereDeletedIsFalse(final Long id) {
        return storeRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(StoreErrorCode.STORE_NOT_FOUND));
    }

    protected Store findByIdWithHeartAndIsDeletedFalse(final Long id) {
        return storeRepository.findByIdWithHeartAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(StoreErrorCode.STORE_NOT_FOUND));
    }

    public Optional<Store> findStoreWithLatitudeAndLongitude(Double latitude, Double longitude) {
        return storeRepository.findByPoint_LatitudeAndPoint_Longitude(latitude, longitude);
    }
}
