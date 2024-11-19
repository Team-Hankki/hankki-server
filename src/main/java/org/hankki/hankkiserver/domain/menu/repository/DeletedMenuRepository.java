package org.hankki.hankkiserver.domain.menu.repository;

import org.hankki.hankkiserver.domain.menu.model.DeletedMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedMenuRepository extends JpaRepository<DeletedMenu, Long> {
}
