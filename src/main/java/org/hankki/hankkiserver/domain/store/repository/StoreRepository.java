package org.hankki.hankkiserver.domain.store.repository;

import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, CustomStoreRepository {
    @Query("select s from Store s where s.id = :id and s.isDeleted = false")
    Optional<Store> findByIdAndIsDeletedIsFalse(Long id);

    @Query("select distinct s from Store s left join fetch s.hearts where s.id = :id and s.isDeleted = false")
    Optional<Store> findByIdWithHeartAndIsDeletedFalse(Long id);

    @Query("select s from Store s where s.point.latitude = :latitude and s.point.longitude = :longitude and s.name = :name and s.isDeleted = false")
    Optional<Store> findByLatitudeAndLongitudeAndNameWhereIsDeletedFalse(double latitude, double longitude, String name);

    boolean existsByPoint_LatitudeAndPoint_LongitudeAndNameAndIsDeleted(double latitude, double longitude, String name, boolean isDeleted);

    @Query("select s from Store s join FavoriteStore fs on s.id = fs.store.id where fs in :favoriteStores and s.isDeleted = false order by fs.id desc ")
    List<Store> findAllByFavoriteStoresAndIsDeletedIsFalseOrderByFavoriteStoreId(@Param("favoriteStores") List<FavoriteStore> favoriteStores);
}