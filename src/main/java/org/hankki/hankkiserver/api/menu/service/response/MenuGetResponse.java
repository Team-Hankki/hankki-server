package org.hankki.hankkiserver.api.menu.service.response;

import org.hankki.hankkiserver.domain.menu.model.Menu;

public record MenuGetResponse (
        long menuId,
        int price,
        String name
){
    public static MenuGetResponse of(final Menu menu) {
        return new MenuGetResponse(menu.getId(), menu.getPrice(), menu.getName());
    }
}
