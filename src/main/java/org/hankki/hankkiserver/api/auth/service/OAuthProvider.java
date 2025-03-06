package org.hankki.hankkiserver.api.auth.service;

import org.hankki.hankkiserver.external.openfeign.oauth.SocialInfoResponse;

public interface OAuthProvider {

    SocialInfoResponse getUserInfo(String token, String name);

    void requestRevoke(String code, String Id);
}
