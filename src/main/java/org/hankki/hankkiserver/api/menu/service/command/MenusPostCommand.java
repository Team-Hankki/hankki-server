package org.hankki.hankkiserver.api.menu.service.command;

import java.util.List;

public record MenusPostCommand (
        long storeId,
        List<MenuPostCommand> menu
){
    public static MenusPostCommand of (final long storeId, final List<MenuPostCommand> menu) {
        return new MenusPostCommand(storeId, menu);
    }
}
