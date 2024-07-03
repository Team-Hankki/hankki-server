package org.hankki.hankkiserver.repository;

import org.hankki.hankkiserver.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE UserInfo ui SET ui.refreshToken = null WHERE ui.user.id = :userId")
    void softDeleteByUserId(Long userId);
}
