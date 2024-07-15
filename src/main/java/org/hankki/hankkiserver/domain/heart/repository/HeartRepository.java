package org.hankki.hankkiserver.domain.heart.repository;

import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    boolean existsByUserAndStore(User user, Store store);
    void deleteByUserAndStore(User user, Store store);
}
