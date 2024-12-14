package org.hankki.hankkiserver.domain.favorite.repository;

import java.util.List;
import java.util.Optional;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  @Modifying
  @Query("delete from Favorite f where f in :favorites")
  void deleteAll(@Param("favorites") List<Favorite> favorites);

  List<Favorite> findByIdIn(@Param("favoriteId") List<Long> favoriteId);

  List<Favorite> findByUserIdOrderByCreatedAtDesc(Long userId);

  @Query("select f from Favorite f where f.id = :favoriteId")
  Optional<Favorite> findByIdWithUser(@Param("favoriteId") Long favoriteId);

  @Query("select f from Favorite f left join fetch f.favoriteStores where f.id = :favoriteId")
  Optional<Favorite> findByIdWithFavoriteStore(@Param("favoriteId") Long favoriteId);

  Optional<Favorite> findByNameAndUser(String title, User user);
}
