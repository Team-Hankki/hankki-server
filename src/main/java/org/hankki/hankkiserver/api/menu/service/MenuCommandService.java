package org.hankki.hankkiserver.api.menu.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenusPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenusGetResponse;
import org.hankki.hankkiserver.api.menu.service.response.MenusPostResponse;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
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
        Menu menu = menuFinder.findByStoreIdAndId(command.storeId(), command.id());
        menuDeleter.deleteMenu(menu);
        if (emptyMenuInStore(command.storeId())) {
            deleteStore(command.storeId());
            return;
        }
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
        List<Menu> menus = command.menu().stream()
                .filter(c -> !validateMenuConflict(findStore, c.name()))
                .map(c -> Menu.create(findStore, c.name(), c.price()))
                .toList();
        menuUpdater.saveAll(menus);
        updateLowestPriceInStore(findStore);
        return MenusPostResponse.of(menus);
    }

    @Transactional(readOnly = true)
    public MenusGetResponse getMenus(final long storeId) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(storeId);
        List<Menu> findmenus = menuFinder.findAllByStore(findStore);
        return MenusGetResponse.of(findmenus);
    }

    private void deleteStore(final long id) {
        Store store = storeFinder.findByIdWhereDeletedIsFalse(id);
        store.updateLowestPrice(0);
        store.softDelete();
    }

    private void updateLowestPriceInStore(final Store findStore) {
        findStore.updateLowestPrice(menuFinder.findLowestPriceByStore(findStore));
    }

    private boolean validateMenuConflict(final Store store, final String menuName) {
        return menuFinder.existsByStoreAndName(store, menuName);
    }

    private boolean emptyMenuInStore(final long storeId) {
        return !menuFinder.existsByStoreId(storeId);
    }
}
