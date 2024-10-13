package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.MenuErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.menu.repository.MenuRepository;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuFinder {

    private final MenuRepository menuRepository;

    public List<Menu> findAllByStore(final Store store) {
        return menuRepository.findAllByStore(store);
    }

    protected Menu findByStoreIdAndId(final Long storeId, final Long id) {
        return menuRepository.findByStoreIdAndId(storeId,id).orElseThrow(() -> new NotFoundException(MenuErrorCode.MENU_NOT_FOUND));
    }

    protected boolean existsByStoreAndName(final Store store, final String name) {
        return menuRepository.existsByStoreAndName(store, name);
    }

    protected int findLowestPriceByStore(Store store) {
        return menuRepository.findLowestPriceByStore(store);
    }
}
