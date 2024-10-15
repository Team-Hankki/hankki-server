package org.hankki.hankkiserver.event.store;

public record DeleteStoreEvent(String name, Long userId) {
    public static DeleteStoreEvent of(String name, Long userId) {
        return new DeleteStoreEvent(name, userId);
    }
}
