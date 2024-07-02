package org.hankki.hankkiserver.repository;

import org.hankki.hankkiserver.domain.User;
import org.hankki.hankkiserver.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPlatformAndSerialId(Platform platform, String platformId);
    Optional<User> findByPlatformAndSerialId(Platform platform, String platformId);
}
