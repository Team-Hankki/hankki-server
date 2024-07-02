package org.hankki.hankkiserver.domain.favorite.repository;

import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
