package org.hankki.hankkiserver.api.menu.service.response;

import java.util.List;

public record MenuListPostResponse(
        List<MenuPostResponse> menus
)
{
    public static MenuListPostResponse of(List<MenuPostResponse> menus) {
        return new MenuListPostResponse(menus);
    }
}