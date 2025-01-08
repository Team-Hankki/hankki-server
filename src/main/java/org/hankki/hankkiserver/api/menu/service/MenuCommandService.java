package org.hankki.hankkiserver.api.menu.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenusPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenusGetResponse;
import org.hankki.hankkiserver.api.menu.service.response.MenusPostResponse;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.api.store.service.StoreUpdater;
import org.hankki.hankkiserver.domain.menu.model.DeletedMenu;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.event.EventPublisher;
import org.hankki.hankkiserver.event.store.DeleteStoreEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuCommandService {

    private final MenuDeleter menuDeleter;
    private final MenuFinder menuFinder;
    private final MenuUpdater menuUpdater;
    private final StoreFinder storeFinder;
    private final StoreUpdater storeUpdater;
    private final DeletedMenuUpdater deletedMenuUpdater;
    private final EventPublisher publisher;

    @Transactional
    public void deleteMenu(final MenuDeleteCommand command) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(command.storeId());
        Menu menu = menuFinder.findByStoreIdAndId(findStore.getId(), command.id());
        menuDeleter.deleteMenu(menu);
        saveToDeletedMenu(menu, findStore.getId());
        updateLowestPriceInStore(findStore);
        checkNoMenuInStore(findStore, command.userId());
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
        List<Menu> menus = filterNewMenu(command, findStore);
        menuUpdater.saveAll(menus);
        updateLowestPriceInStore(findStore);
        return MenusPostResponse.of(menus);
    }

    @Transactional(readOnly = true)
    public MenusGetResponse getMenus(final long storeId) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(storeId);
        List<Menu> findMenus = menuFinder.findAllByStore(findStore);
        return MenusGetResponse.of(findMenus);
    }

    private void updateLowestPriceInStore(final Store findStore) {
        findStore.updateLowestPrice(menuFinder.findLowestPriceByStore(findStore));
    }

    private List<Menu> filterNewMenu(final MenusPostCommand command, final Store store) {
        Set<String> allMenuNames = parseAllMenuNames(store);
        return command.menu().stream()
                .filter(c -> !validateMenuConflict(allMenuNames, c.name()))
                .map(c -> Menu.create(store, c.name(), c.price()))
                .toList();
    }

    private Set<String> parseAllMenuNames(final Store store) {
        return menuFinder.findAllByStore(store).stream()
                .map(Menu::getName)
                .collect(Collectors.toSet());
    }

    private boolean validateMenuConflict(final Set<String> menus, final String menuName) {
        return menus.contains(menuName);
    }

    private void checkNoMenuInStore(final Store store, final long userId) {
        if (!menuFinder.existsByStoreId(store.getId())) {
            storeUpdater.deleteStore(store.getId());
            publisher.publish(DeleteStoreEvent.of(store.getName(), userId));
        }
    }

    private void saveToDeletedMenu(final Menu menu, final long storedId) {
        DeletedMenu deletedMenu = DeletedMenu.create(menu.getName(), menu.getPrice(), storedId);
        deletedMenuUpdater.save(deletedMenu);
    }
}
