package org.hankki.hankkiserver.domain.store.repository;

import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("select s from Store s where s.id = :id and s.isDeleted = false")
    Optional<Store> findByIdAndIsDeletedIsFalse(Long id);

    @Query("select distinct s from Store s left join fetch s.hearts where s.id = :id and s.isDeleted = false")
    Optional<Store> findByIdWithHeartAndIsDeletedFalse(Long id);

    @Query("select s from Store s where s.point.latitude = :latitude and s.point.longitude = :longitude")
    Optional<Store> findByPoint_LatitudeAndPoint_Longitude(double latitude, double longitude);

    Optional<Store> findByName(String name);
}
