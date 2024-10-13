package org.hankki.hankkiserver.api.menu.service.command;

public record MenuDeleteCommand(
        long storeId,
        long id
) {
    public static MenuDeleteCommand of(long storeId, long id) {
        return new MenuDeleteCommand(storeId, id);
    }
}
