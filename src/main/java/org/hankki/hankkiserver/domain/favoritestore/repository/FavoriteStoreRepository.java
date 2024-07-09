package org.hankki.hankkiserver.domain.favoritestore.repository;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {

  @Modifying
  @Query("delete from FavoriteStore fs where fs.favorite in :favorites")
  void deleteAllByFavorites(@Param("favorites")List<Favorite> favorites);
}
