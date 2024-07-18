package org.hankki.hankkiserver.external.openfeign.apple.dto;

public record AppleTokenRequest(
        String code,
        String client_id,
        String client_secret,
        String grant_type
) {
    private static final String GRANT_TYPE = "authorization_code";

    public static AppleTokenRequest of(String code, String client_id, String client_secret) {
        return new AppleTokenRequest(code, client_id, client_secret, GRANT_TYPE);
    }
}
