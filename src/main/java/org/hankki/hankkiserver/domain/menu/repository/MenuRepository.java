package org.hankki.hankkiserver.domain.menu.repository;

import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
