package org.hankki.hankkiserver.external.openfeign.oauth;

public interface OAuthProvider {

    SocialInfoDto getUserInfo(String token, String name);

    void requestRevoke(String code, String Id);
}
