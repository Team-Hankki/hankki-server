package org.hankki.hankkiserver.api.store.service.response;

import org.hankki.hankkiserver.domain.menu.model.Menu;

public record MenuResponse(
        Long id,
        String name,
        int price
) {
    public static MenuResponse of(Menu menu) {
        return new MenuResponse(menu.getId(), menu.getName(), menu.getPrice());
    }
}
