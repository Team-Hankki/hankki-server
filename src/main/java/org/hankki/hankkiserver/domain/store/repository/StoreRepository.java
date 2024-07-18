package org.hankki.hankkiserver.domain.store.repository;

import java.util.List;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long>, CustomStoreRepository {
    @Query("select s from Store s where s.id = :id and s.isDeleted = false")
    Optional<Store> findByIdAndIsDeletedIsFalse(Long id);

    @Query("select distinct s from Store s left join fetch s.hearts where s.id = :id and s.isDeleted = false")
    Optional<Store> findByIdWithHeartAndIsDeletedFalse(Long id);

    @Query("select s from Store s where s.point.latitude = :latitude and s.point.longitude = :longitude")
    Optional<Store> findByPoint_LatitudeAndPoint_Longitude(double latitude, double longitude);

    boolean existsByPoint_LatitudeAndPoint_Longitude(double latitude, double longitude);

    @Query("select s from Store s where s.id in :ids and s.isDeleted = false order by s.createdAt desc")
    List<Store> findAllByIdAndIsDeletedIsFalseOrderByCreatedAtDesc(@Param("ids") List<Long> ids);
}
