package org.hankki.hankkiserver.event.store;

public record DeleteStoreEvent(String name) {
    public static DeleteStoreEvent of(String name) {
        return new DeleteStoreEvent(name);
    }
}
