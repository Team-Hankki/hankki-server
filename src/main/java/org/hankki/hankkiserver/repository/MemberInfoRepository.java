package org.hankki.hankkiserver.repository;

import org.hankki.hankkiserver.domain.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

    Optional<MemberInfo> findByMemberId(Long memberId);
    void deleteByMemberId(Long memberId);
}
