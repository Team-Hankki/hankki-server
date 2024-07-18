package org.hankki.hankkiserver.external.openfeign.apple;

import org.hankki.hankkiserver.external.openfeign.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleRevokeRequest;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleTokenRequest;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKey();

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AppleTokenResponse getAppleTokens(AppleTokenRequest request);

    @PostMapping(value = "/revoke", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void revoke(AppleRevokeRequest request);
}
