package org.hankki.hankkiserver.api.menu.service.command;

public record MenuPostCommand(
        long storeId,
        String name,
        int price
) {
    public static MenuPostCommand of(final long storeId, final String name, final int price) {
        return new MenuPostCommand(storeId, name, price);
    }
}
