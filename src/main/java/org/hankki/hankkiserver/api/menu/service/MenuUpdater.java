package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuUpdater {

    private final MenuRepository menuRepository;

    public void saveAll(final List<Menu> menus) {
        menuRepository.saveAll(menus);
    }
}
