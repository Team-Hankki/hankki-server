package org.hankki.hankkiserver.domain.heart.repository;

import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserIdAndStoreId(Long userId, Long storeId);
}
