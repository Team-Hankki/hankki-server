package org.hankki.hankkiserver.domain.favorite.repository;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  @Modifying
  @Query("delete from Favorite f where f in :favorites")
  void deleteAll(@Param("favorites") List<Favorite> favorites);
}
