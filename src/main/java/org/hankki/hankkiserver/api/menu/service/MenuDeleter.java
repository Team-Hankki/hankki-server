package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.menu.model.Menu;
import org.hankki.hankkiserver.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuDeleter {

    private final MenuRepository menuRepository;

    protected void deleteMenu(final Menu menu) {
        menuRepository.delete(menu);
    }
}
