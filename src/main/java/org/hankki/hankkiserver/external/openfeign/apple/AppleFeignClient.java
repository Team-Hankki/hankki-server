package org.hankki.hankkiserver.external.openfeign.apple;

import org.hankki.hankkiserver.external.openfeign.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleTokenRequest;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleRevokeRequest;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKey();

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    AppleTokenResponse getAppleTokens(@RequestBody AppleTokenRequest request);

    @PostMapping(value = "/revoke", consumes = "application/x-www-form-urlencoded")
    void revoke(AppleRevokeRequest request);
}
