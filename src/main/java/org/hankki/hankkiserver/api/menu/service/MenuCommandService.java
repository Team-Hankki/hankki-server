package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenuPostResponse;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.common.code.MenuErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
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
        if (!validateMenuExistInStore(command.storeId(), findMenu)) {
            throw new BadRequestException(MenuErrorCode.MENU_NOT_FOUND);
        }
        menuDeleter.deleteMenu(findMenu);
        updateLowestPriceInStore(command.storeId());
    }

    @Transactional
    public void modifyMenu(final MenuPatchCommand command) {
        Menu findMenu  = menuFinder.findById(command.id());
        if (!validateMenuExistInStore(command.storeId(), findMenu)) {
            throw new BadRequestException(MenuErrorCode.MENU_NOT_FOUND);
        }
        findMenu.update(command.name(), command.price());
        updateLowestPriceInStore(command.storeId());
    }

    @Transactional
    public MenuPostResponse createMenu(final MenuPostCommand command) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(command.storeId());
        if (validateConflictMenu(findStore, command.name())) {
            throw new ConflictException(MenuErrorCode.ALREADY_EXISTED_MENU);
        }
        Menu menu = Menu.create(findStore, command.name(), command.price());
        menuUpdater.save(menu);
        updateLowestPriceInStore(findStore, menu);
        return MenuPostResponse.of(menu);
    }

    private void updateLowestPriceInStore(final long storeId) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(storeId);
        int lowestPrice = menuFinder.findAllByStore(findStore).stream()
                .mapToInt(Menu::getPrice)
                .min()
                .orElse(0);
        findStore.updateLowestPrice(lowestPrice);
    }

    private void updateLowestPriceInStore(final Store store, final Menu menu) {
        if (store.getLowestPrice() > menu.getPrice()) {
            store.updateLowestPrice(menu.getPrice());
        }
    }

    private boolean validateMenuExistInStore(final long storeId, final Menu menu) {
        return storeId == menu.getStore().getId();
    }

    private boolean validateConflictMenu(final Store store, final String name) {
        return menuFinder.existsByStoreAndName(store, name);
    }
}
