package org.hankki.hankkiserver.api.menu.service.command;

public record MenuPatchRequest(
        long id,
        String name,
        int price
) {
    public static MenuPatchRequest of(final long id, final String name, final int price) {
        return new MenuPatchRequest(id, name, price);
    }
}
