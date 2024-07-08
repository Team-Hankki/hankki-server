package org.hankki.hankkiserver.domain.user.repository;

import org.hankki.hankkiserver.domain.user.model.MemberStatus;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPlatformAndSerialIdAndMemberStatus(Platform platform, String serialId, MemberStatus memberStatus);
    Optional<User> findByPlatformAndSerialIdAndMemberStatus(Platform platform, String serialId, MemberStatus memberStatus);
    Optional<User> findByIdAndMemberStatus(Long Userid, MemberStatus memberStatus);
}
