package org.hankki.hankkiserver.domain.heart.repository;

import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    boolean existsByUserAndStore(User user, Store store);
    void deleteByUserAndStore(User user, Store store);

    @Query("select h from Heart h join fetch h.store s join fetch s.storeImages " +
            "where h.user.id = :userId and s.isDeleted = false order by h.id desc")
    List<Heart> findHeartedStoresByUserId(Long userId);
}
