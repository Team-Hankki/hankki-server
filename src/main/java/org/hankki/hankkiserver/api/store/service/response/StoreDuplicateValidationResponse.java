package org.hankki.hankkiserver.api.store.service.response;

public record StoreDuplicateValidationResponse(
        Long id,
        boolean isRegistered
) {
    public static StoreDuplicateValidationResponse of(Long id, boolean isRegistered) {
        return new StoreDuplicateValidationResponse(id, isRegistered);
    }
}
