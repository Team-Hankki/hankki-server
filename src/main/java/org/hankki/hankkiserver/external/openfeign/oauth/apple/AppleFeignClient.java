package org.hankki.hankkiserver.external.openfeign.oauth.apple;

import org.hankki.hankkiserver.external.openfeign.oauth.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.oauth.apple.dto.AppleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKey();

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AppleTokenResponse getAppleTokens(@RequestParam(value = "code") String code,
                                      @RequestParam(value = "client_id") String client_id,
                                      @RequestParam(value = "client_secret") String client_secret,
                                      @RequestParam(value = "grant_type") String grant_type);

    @PostMapping(value = "/revoke", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void revoke(@RequestParam(value = "token") String token,
                @RequestParam(value = "client_id") String client_id,
                @RequestParam(value = "client_secret") String client_secret,
                @RequestParam(value = "token_type_hint") String token_type_hint);
}
