package org.hankki.hankkiserver.domain.user.repository;

import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE UserInfo ui SET ui.refreshToken = null, ui.nickname = '알 수 없음' WHERE ui.user.id = :userId")
    void softDeleteByUserId(Long userId);
}
