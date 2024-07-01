package org.hankki.hankkiserver.repository;

import org.hankki.hankkiserver.domain.Member;
import org.hankki.hankkiserver.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPlatformAndSerialId(Platform platform, String platformId);
    Optional<Member> findByPlatformAndSerialId(Platform platform, String platformId);
}
