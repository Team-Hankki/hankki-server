package org.hankki.hankkiserver.repository;

import org.hankki.hankkiserver.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
