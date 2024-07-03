package org.hankki.hankkiserver.oauth.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.oauth.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.oauth.dto.SocialInfoDto;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOAuthProvider {

    private final AppleFeignClient appleFeignClient;
    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;

    public SocialInfoDto getAppleUserInfo(final String identityToken, String name) {
        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKey();
        PublicKey applePublicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys);
        Claims claims = appleIdentityTokenParser.parsePublicKeyAndGetClaims(identityToken, applePublicKey);
        return SocialInfoDto.of(
                claims.get("sub").toString(),
                name,
                claims.get("email").toString());
    }
}
