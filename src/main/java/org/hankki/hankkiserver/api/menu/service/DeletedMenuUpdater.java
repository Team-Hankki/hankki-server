package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.menu.model.DeletedMenu;
import org.hankki.hankkiserver.domain.menu.repository.DeletedMenuRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletedMenuUpdater {

    private final DeletedMenuRepository deletedMenuRepository;

    public void save(final DeletedMenu deletedMenu) {
        deletedMenuRepository.save(deletedMenu);
    }
}
