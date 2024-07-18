package org.hankki.hankkiserver.external.openfeign.apple.dto;

public record AppleRevokeRequest(
        String token,
        String client_id,
        String client_secret,
        String token_type_hint
) {

    private static final String TOKEN_TYPE_HINT = "refresh_token";

    public static AppleRevokeRequest of(String token, String client_id, String client_secret) {
        return new AppleRevokeRequest(token, client_id, client_secret, TOKEN_TYPE_HINT);
    }
}
