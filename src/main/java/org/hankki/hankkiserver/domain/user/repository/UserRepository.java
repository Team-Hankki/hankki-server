package org.hankki.hankkiserver.domain.user.repository;

import org.hankki.hankkiserver.domain.user.model.UserStatus;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPlatformAndSerialId(Platform platform, String serialId);

    boolean existsByPlatformAndSerialIdAndMemberStatus(Platform platform, String serialId, UserStatus userStatus);
}
