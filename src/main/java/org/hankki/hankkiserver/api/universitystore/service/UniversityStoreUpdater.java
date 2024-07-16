package org.hankki.hankkiserver.api.universitystore.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hankki.hankkiserver.domain.universitystore.repository.UniversityStoreRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityStoreUpdater {

    private final UniversityStoreRepository universityStoreRepository;

    public void save(UniversityStore universityStore) {
        universityStoreRepository.save(universityStore);
    }
}
