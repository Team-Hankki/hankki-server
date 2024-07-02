package org.hankki.hankkiserver.domain.store.repository;

import org.hankki.hankkiserver.domain.store.model.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
}
