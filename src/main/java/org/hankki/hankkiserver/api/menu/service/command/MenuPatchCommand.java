package org.hankki.hankkiserver.api.menu.service.command;

public record MenuPatchCommand(
        long storeId,
        long id,
        String name,
        int price
) {
    public static MenuPatchCommand of(final long storeId, final long id, final String name, final int price) {
        return new MenuPatchCommand(storeId, id, name, price);
    }
}
