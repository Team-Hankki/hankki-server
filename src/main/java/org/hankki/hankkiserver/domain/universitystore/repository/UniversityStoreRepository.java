package org.hankki.hankkiserver.domain.universitystore.repository;

import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityStoreRepository extends JpaRepository<UniversityStore, Long> {

    Optional<UniversityStore> findByUniversityIdAndStore(Long universityId, Store store);
}
