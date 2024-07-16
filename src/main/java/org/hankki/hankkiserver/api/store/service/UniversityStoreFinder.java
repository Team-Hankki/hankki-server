package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hankki.hankkiserver.domain.universitystore.repository.UniversityStoreRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UniversityStoreFinder {

    private final UniversityStoreRepository universityStoreRepository;

    protected Optional<UniversityStore> findByUniversityIdAndStore(final Long universityId, final Store store) {
        return universityStoreRepository.findByUniversityIdAndStore(universityId, store);
    }
}
