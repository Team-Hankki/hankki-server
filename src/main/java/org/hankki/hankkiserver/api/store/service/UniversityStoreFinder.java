package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.universitystore.repository.UniversityStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityStoreFinder {

    private final UniversityStoreRepository universityStoreRepository;

    protected boolean findByUniversityIdAndStore(final Long universityId, final Store store) {
        return universityStoreRepository.existsByUniversityIdAndStore(universityId, store);
    }
}
