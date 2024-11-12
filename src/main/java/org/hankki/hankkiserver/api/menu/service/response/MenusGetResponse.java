package org.hankki.hankkiserver.api.menu.service.response;

import java.util.List;

public record MenusGetResponse(
        List<MenuGetResponse> menus
) {
    public static MenusGetResponse of(List<MenuGetResponse> menus) {
        return new MenusGetResponse(menus);
    }
}
