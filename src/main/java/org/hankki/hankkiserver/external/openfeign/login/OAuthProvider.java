package org.hankki.hankkiserver.external.openfeign.login;

import org.hankki.hankkiserver.external.openfeign.login.dto.SocialInfoDto;

public interface OAuthProvider {

    SocialInfoDto getUserInfo(String token, String name);

    void requestRevoke(String code, String Id);
}
