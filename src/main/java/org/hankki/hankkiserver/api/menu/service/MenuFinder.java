package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
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
}
