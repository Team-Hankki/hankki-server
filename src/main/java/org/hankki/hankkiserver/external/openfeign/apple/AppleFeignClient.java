package org.hankki.hankkiserver.external.openfeign.apple;

import org.hankki.hankkiserver.external.openfeign.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKey();

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AppleTokenResponse getAppleTokens(@RequestPart(value = "code") String code,
                                      @RequestPart(value = "client_id") String client_id,
                                      @RequestPart(value = "client_secret") String client_secret,
                                      @RequestPart(value = "grant_type") String grant_type);

    @PostMapping(value = "/revoke", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void revoke(@RequestPart(value = "token") String token,
                @RequestPart(value = "client_id") String client_id,
                @RequestPart(value = "client_secret") String client_secret,
                @RequestPart(value = "token_type_hint") String token_type_hint);
}
