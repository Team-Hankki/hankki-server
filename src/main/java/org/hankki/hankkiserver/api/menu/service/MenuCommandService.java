package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenusPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenuListPostResponse;
import org.hankki.hankkiserver.api.menu.service.response.MenuPostResponse;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.common.code.MenuErrorCode;
import org.hankki.hankkiserver.common.exception.ConflictException;
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
    public MenuListPostResponse createMenus(final MenusPostCommand commands) {
        Store findStore = storeFinder.findByIdWhereDeletedIsFalse(commands.storeId());
        List<MenuPostResponse> menus = commands.menu().stream()
                .map(command -> {
                    validateMenuNotConflict(findStore, command.name());
                    Menu menu = Menu.create(findStore, command.name(), command.price());
                    menuUpdater.save(menu);
                    return MenuPostResponse.of(menu);
                })
                .toList();
        updateLowestPriceInStore(findStore);
        return MenuListPostResponse.of(menus);
    }


    private void updateLowestPriceInStore(final Store findStore) {
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

    private void validateMenuNotConflict(Store store, String menuName) {
        if (menuFinder.existsByStoreAndName(store, menuName)) {
            throw new ConflictException(MenuErrorCode.ALREADY_EXISTED_MENU);
        }
    }
}
