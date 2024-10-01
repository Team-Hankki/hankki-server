package org.hankki.hankkiserver.api.menu.service.response;

import org.hankki.hankkiserver.domain.menu.model.Menu;

public record MenuPostResponse (
        long id,
        String name,
        int price
) {

    public static MenuPostResponse of(final Menu menu) {
        return new MenuPostResponse(menu.getId(), menu.getName(), menu.getPrice());
    }
}
