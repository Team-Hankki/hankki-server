package org.hankki.hankkiserver.domain.favoritestore.repository;

import java.util.List;
import java.util.Optional;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {

  @Modifying
  @Query("delete from FavoriteStore fs where fs.favorite in :favorites")
  void deleteAllByFavorites(@Param("favorites") List<Favorite> favorites);

  @Query("select count(fs) from FavoriteStore fs where fs.favorite = :favorite")
  int countByFavorite(@Param("favorite") Favorite favorite);

  @Query("select fs from FavoriteStore fs where fs.favorite.id = :favoriteId and fs.store.id = :storeId")
  Optional<FavoriteStore> findByFavoriteIdAndStoreId(@Param("favoriteId") Long favoriteId, @Param("storeId") Long storeId);

  @Query("select exists (select f from FavoriteStore f where f.favorite.id = :favoriteId and f.store.id = :storeId)")
  boolean isExist(@Param("favoriteId") Long favoriteId, @Param("storeId") Long storeId);
}