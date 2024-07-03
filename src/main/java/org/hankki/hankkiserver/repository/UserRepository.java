package org.hankki.hankkiserver.repository;

import org.hankki.hankkiserver.domain.User;
import org.hankki.hankkiserver.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPlatformAndSerialIdAndIsDeleted(Platform platform, String platformId, boolean isDeleted);
    Optional<User> findByPlatformAndSerialId(Platform platform, String platformId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :userId")
    void softDeleteById(@Param("userId") Long userId);
}
