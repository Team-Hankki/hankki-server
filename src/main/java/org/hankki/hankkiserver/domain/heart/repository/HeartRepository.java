package org.hankki.hankkiserver.domain.heart.repository;

import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
}
