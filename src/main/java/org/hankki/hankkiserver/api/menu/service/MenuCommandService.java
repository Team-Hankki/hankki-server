package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
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
    public void deleteMenu(final MenuDeleteCommand command) {
        Menu findMenu = menuFinder.findById(command.id());
        Store findStore = validateMenuExistInStore(command.storeId(), findMenu);
        menuDeleter.deleteMenu(findMenu);
        updateLowestPriceInStore(findStore);
    }

    @Transactional
    public void modifyMenu(final MenuPatchCommand command) {
        Menu findMenu  = menuFinder.findById(command.id());
        Store findStore = validateMenuExistInStore(command.storeId(), findMenu);
        findMenu.update(command.name(), command.price());
        updateLowestPriceInStore(findStore);
    }

    @Transactional
    public MenuPostResponse createMenu(final MenuPostCommand request) {
        Store findStore = validateConflictMenu(request.storeId(), request.name());
        Menu menu = Menu.create(findStore, request.name(), request.price());
        menuUpdater.save(menu);
        updateLowestPriceInStore(findStore, menu);
        return MenuPostResponse.of(menu);
    }

    private void updateLowestPriceInStore(final Store store) {
        int lowestPrice = menuFinder.findByStore(store).stream()
                .mapToInt(Menu::getPrice)
                .min()
                .orElse(0);
        store.updateLowestPrice(lowestPrice);
    }

    private void updateLowestPriceInStore(final Store store, final Menu menu) {
        if (store.getLowestPrice() > menu.getPrice()) {
            store.updateLowestPrice(menu.getPrice());
        }
    }

    private Store validateMenuExistInStore(final long storeId, final Menu menu) {
        Store store = storeFinder.findByIdWhereDeletedIsFalse(storeId);
        menuFinder.findByStoreAndMenu(store, menu);
        return store;
    }

    private Store validateConflictMenu(final long storeId, final String name) {
        Store store = storeFinder.findByIdWhereDeletedIsFalse(storeId);
        menuFinder.findByStoreAndName(store, name).ifPresent(menu -> {
                    throw new ConflictException(MenuErrorCode.ALREADY_EXISTED_MENU);
        });
        return store;
    }
}
