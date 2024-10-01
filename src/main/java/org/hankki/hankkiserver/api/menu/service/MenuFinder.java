package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.MenuErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.menu.repository.MenuRepository;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MenuFinder {

    private final MenuRepository menuRepository;

    public List<Menu> findAllByStore(final Store store) {
        return menuRepository.findAllByStore(store);
    }

    protected Menu findById(final Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new NotFoundException(MenuErrorCode.MENU_NOT_FOUND));
    }

    protected List<Menu> findByStore(final Store store) {
        return menuRepository.findByStoreId(store.getId());
    }

    protected Menu findByStoreAndMenu(final Store store, final Menu menu) {
        return menuRepository.findByStoreAndId(store, menu.getId()).orElseThrow(() -> new BadRequestException(MenuErrorCode.MENU_NOT_FOUND));
    }

    protected Optional<Menu> findByStoreAndName(final Store store, final String name) {
        return menuRepository.findByStoreAndName(store, name);
    }
}
