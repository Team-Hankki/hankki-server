package org.hankki.hankkiserver.api.menu.service.response;

import org.hankki.hankkiserver.domain.menu.model.Menu;

import java.util.List;

public record MenusPostResponse(
        List<MenuPostResponse> menuList
) {
    public static MenusPostResponse of(List<Menu> menus) {
        List<MenuPostResponse> menuResponses = menus.stream()
                .map(MenuPostResponse::of)
                .toList();
        return new MenusPostResponse(menuResponses);
    }
}