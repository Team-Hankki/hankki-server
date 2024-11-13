package org.hankki.hankkiserver.api.menu.service.command;

public record MenuDeleteCommand(
        long storeId,
        long id,
        long userId
) {
    public static MenuDeleteCommand of(long storeId, long id, long userId) {
        return new MenuDeleteCommand(storeId, id, userId);
    }
}
