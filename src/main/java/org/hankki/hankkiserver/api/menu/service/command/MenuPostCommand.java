package org.hankki.hankkiserver.api.menu.service.command;

public record MenuPostCommand(
        String name,
        int price
) {
    public static MenuPostCommand of(final String name, final int price) {
        return new MenuPostCommand(name, price);
    }
}
