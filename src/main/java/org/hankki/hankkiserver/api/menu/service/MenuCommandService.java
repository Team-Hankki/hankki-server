package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenusPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenuPostResponse;
import org.hankki.hankkiserver.api.menu.service.response.MenusPostResponse;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuCommandService {

    private final MenuDeleter menuDeleter;
    private final MenuFinder menuFinder;
    private final MenuUpdater menuUpdater;
    private final StoreFinder storeFinder;

    @Transactional
    public void deleteMenu(final MenuDeleteCommand command) {
        Menu menu = menuFinder.findByStoreIdAndId(command.storeId(), command.id());
        menuDeleter.deleteMenu(menu);
        updateLowestPriceInStore(storeFinder.findByIdWhereDeletedIsFalse(command.storeId()));
    }

    @Transactional
    public void modifyMenu(final MenuPatchCommand command) {
        Menu menu = menuFinder.findByStoreIdAndId(command.storeId(), command.id());
        menu.update(command.name(), command.price());
        updateLowestPriceInStore(storeFinder.findByIdWhereDeletedIsFalse(command.storeId()));
    }

    @Transactional
    public MenusPostResponse createMenus(final MenusPostCommand command) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(command.storeId());
        List<MenuPostResponse> menus = command.menu().stream()
                .filter(c -> !validateMenuConflict(findStore, c.name()))
                .map(c -> {
                    Menu menu = Menu.create(findStore, c.name(), c.price());
                    menuUpdater.save(menu);
                    return MenuPostResponse.of(menu);
                })
                .toList();
        updateLowestPriceInStore(findStore);
        return MenusPostResponse.of(menus);
    }

    private void updateLowestPriceInStore(final Store findStore) {
        findStore.updateLowestPrice(menuFinder.findLowestPriceByStore(findStore));
    }


    private boolean validateMenuConflict(Store store, String menuName) {
        return menuFinder.existsByStoreAndName(store, menuName);
    }
}