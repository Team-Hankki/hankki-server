package org.hankki.hankkiserver.api.menu.service.response;

import java.util.List;
import org.hankki.hankkiserver.domain.menu.model.Menu;

public record MenusGetResponse(
        List<MenuGetResponse> menus
) {
    public static MenusGetResponse of(final List<Menu> menus) {
        List<MenuGetResponse> findmenus = menus.stream()
                .map(MenuGetResponse::of)
                .toList();
        return new MenusGetResponse(findmenus);
    }
}
