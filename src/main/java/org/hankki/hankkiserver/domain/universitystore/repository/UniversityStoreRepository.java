package org.hankki.hankkiserver.domain.universitystore.repository;

import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UniversityStoreRepository extends JpaRepository<UniversityStore, Long> {

    @Query("select us from UniversityStore us join fetch us.store s where us.id = :id and s.point.latitude = :latitude and s.point.longitude = :longitude")
    Optional<UniversityStore> findUniversityStoreByLatitudeAndLongitude(Long id, Double latitude, Double longitude);
}
