package org.hankki.hankkiserver.domain.favoritestore.repository;

import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {
}
