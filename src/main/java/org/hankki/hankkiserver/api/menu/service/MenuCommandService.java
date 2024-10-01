package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchRequest;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuCommandService {

    private final MenuDeleter menuDeleter;
    private final MenuFinder menuFinder;
    private final MenuUpdater menuUpdater;

    @Transactional
    public void deleteMenu(final long menuId) {
        menuDeleter.deleteMenu(menuFinder.findById(menuId));
    }

    @Transactional
    public void modifyMenu(final MenuPatchRequest request) {
        Menu findMenu  = menuFinder.findById(request.id());
        findMenu.update(request.name(), request.price());
    }
}
