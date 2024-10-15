package org.hankki.hankkiserver.event.store;

public record CreateStoreEvent (String storeName,
                                String universityName) {
    public static CreateStoreEvent of(String storeName, String universityName) {
        return new CreateStoreEvent(storeName, universityName);
    }
}
