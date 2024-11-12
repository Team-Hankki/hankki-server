package org.hankki.hankkiserver.domain.menu.repository;

import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);
    Optional<Menu> findByStoreIdAndId(long storeId, long id);
    boolean existsByStoreAndName(Store store, String name);

    @Query("SELECT MIN(m.price) FROM Menu m WHERE m.store = :store")
    int findLowestPriceByStore(Store store);

    boolean existsByStoreId(long storeId);
}
