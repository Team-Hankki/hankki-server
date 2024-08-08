package org.hankki.hankkiserver.external.openfeign.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.external.openfeign.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.apple.dto.AppleTokenResponse;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppleOAuthProvider {

    private final AppleFeignClient appleFeignClient;
    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;;

    @Value("${oauth.apple.client-id}")
    private String clientId;

    public SocialInfoDto getAppleUserInfo(final String identityToken, final String name) {
        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKey();
        PublicKey applePublicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys);
        Claims claims = appleIdentityTokenParser.parsePublicKeyAndGetClaims(identityToken, applePublicKey);
        return SocialInfoDto.of(
                claims.get("sub").toString(),
                name,
                claims.get("email").toString());
    }

    public String getAppleRefreshToken(final String code, final String clientSecret) {
        try {
            AppleTokenResponse appleTokenResponse = appleFeignClient.getAppleTokens(code, clientId, clientSecret, "authorization_code");
            log.info("Apple token response: {}", appleTokenResponse);
            return appleTokenResponse.refreshToken();
        } catch (Exception e) {
            log.error("Failed to get apple refresh token.");
            throw new BadRequestException(AuthErrorCode.FAILED_TO_LOAD_PRIVATE_KEY);
        }
    }

    public void requestRevoke(final String refreshToken, final String clientSecret) {
        appleFeignClient.revoke(refreshToken, clientId, clientSecret, "refresh_token");
    }
}
