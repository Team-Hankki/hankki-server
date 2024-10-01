package org.hankki.hankkiserver.api.menu.service.command;

public record MenuPatchCommand(
        long id,
        String name,
        int price
) {
    public static MenuPatchCommand of(final long id, final String name, final int price) {
        return new MenuPatchCommand(id, name, price);
    }
}
