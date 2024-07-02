package org.hankki.hankkiserver.domain.store.repository;

import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
