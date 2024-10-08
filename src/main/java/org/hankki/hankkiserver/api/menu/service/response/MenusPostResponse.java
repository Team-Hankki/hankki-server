package org.hankki.hankkiserver.api.menu.service.response;

import java.util.List;

public record MenusPostResponse(
        List<MenuPostResponse> menuList
) {
    public static MenusPostResponse of(List<MenuPostResponse> menus) {
        return new MenusPostResponse(menus);
    }
}