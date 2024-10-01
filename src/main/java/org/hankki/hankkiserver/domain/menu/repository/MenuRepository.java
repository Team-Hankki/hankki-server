package org.hankki.hankkiserver.domain.menu.repository;

import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);
    Optional<Menu> findByStoreAndId(Store store, Long id);
    Optional<Menu> findByStoreAndName(Store store, String name);
}
