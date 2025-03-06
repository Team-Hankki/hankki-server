package org.hankki.hankkiserver.domain.user.repository;

import java.util.Optional;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPlatformAndSerialId(Platform platform, String serialId);
}
