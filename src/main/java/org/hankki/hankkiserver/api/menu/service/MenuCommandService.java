package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenuPostResponse;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.common.code.MenuErrorCode;
import org.hankki.hankkiserver.common.exception.ConflictException;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuCommandService {

    private final MenuDeleter menuDeleter;
    private final MenuFinder menuFinder;
    private final MenuUpdater menuUpdater;
    private final StoreFinder storeFinder;

    @Transactional
    public void deleteMenu(final long menuId) {
        menuDeleter.deleteMenu(menuFinder.findById(menuId));
    }

    @Transactional
    public void modifyMenu(final MenuPatchCommand request) {
        Menu findMenu  = menuFinder.findById(request.id());
        findMenu.update(request.name(), request.price());
    }

    @Transactional
    public MenuPostResponse createMenu(final MenuPostCommand request) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(request.storeId());
        validateConfliceMenu(findStore, request.name());
        Menu menu = Menu.create(findStore, request.name(), request.price());
        menuUpdater.save(menu);
        return MenuPostResponse.of(menu);
    }

    private void validateConfliceMenu(Store store, String name) {
        menuFinder.findByStoreAndName(store, name).ifPresent(menu -> {
                    throw new ConflictException(MenuErrorCode.ALREADY_EXISTED_MENU);
        });
    }
}
