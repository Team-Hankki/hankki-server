package org.hankki.hankkiserver.api.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuCommandService {

    private final MenuDeleter menuDeleter;
    private final MenuFinder menuFinder;

    @Transactional
    public void deleteMenu(final long menuId) {
        menuDeleter.deleteMenu(menuFinder.findById(menuId));
    }
}
