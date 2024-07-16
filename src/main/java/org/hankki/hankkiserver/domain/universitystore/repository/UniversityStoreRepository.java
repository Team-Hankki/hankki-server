package org.hankki.hankkiserver.domain.universitystore.repository;

import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityStoreRepository extends JpaRepository<UniversityStore, Long> {

    boolean existsByUniversityIdAndStore(Long universityId, Store store);
}
