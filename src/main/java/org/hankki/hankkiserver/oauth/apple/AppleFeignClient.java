package org.hankki.hankkiserver.oauth.apple;

import org.hankki.hankkiserver.oauth.apple.dto.AppleRevokeRequest;
import org.hankki.hankkiserver.oauth.apple.dto.AppleTokenRequest;
import org.hankki.hankkiserver.oauth.apple.dto.AppleTokenResponse;
import org.hankki.hankkiserver.oauth.apple.dto.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${oauth.apple.name}", url = "${oauth.apple.url}")
public interface AppleFeignClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKey();

    @PostMapping(value = "token", consumes = "application/x-www-form-urlencoded")
    AppleTokenResponse getAppleTokens(AppleTokenRequest request);

    @PostMapping(value = "revoke", consumes = "application/x-www-form-urlencoded")
    void revoke(AppleRevokeRequest request);
}
