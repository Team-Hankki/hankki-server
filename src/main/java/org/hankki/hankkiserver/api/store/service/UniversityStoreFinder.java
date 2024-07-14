package org.hankki.hankkiserver.api.store.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hankki.hankkiserver.domain.universitystore.repository.UniversityStoreRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UniversityStoreFinder {

    private final UniversityStoreRepository universityStoreRepository;

    public Optional<UniversityStore> findUniversityStoreWithLatitudeAndLongitude(Long id, Double latitude, Double longitude) {
        return universityStoreRepository.findUniversityStoreByLatitudeAndLongitude(id, latitude, longitude);
    }

}
