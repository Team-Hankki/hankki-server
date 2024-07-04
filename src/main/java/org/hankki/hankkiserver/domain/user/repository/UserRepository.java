package org.hankki.hankkiserver.domain.user.repository;

import org.hankki.hankkiserver.domain.user.model.MemberStatus;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPlatformAndSerialIdAndMemberStatus(Platform platform, String platformId, MemberStatus memberStatus);
    Optional<User> findByPlatformAndSerialId(Platform platform, String platformId);

    @Modifying
    @Query("UPDATE User u SET u.memberStatus = :memberStatus WHERE u.id = :userId")
    void softDeleteById(@Param("userId") Long userId, @Param("memberStatus") MemberStatus memberStatus);
}
