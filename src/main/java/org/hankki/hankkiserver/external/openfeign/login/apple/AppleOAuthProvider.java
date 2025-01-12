package org.hankki.hankkiserver.external.openfeign.login.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.external.openfeign.login.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.login.apple.dto.AppleTokenResponse;
import org.hankki.hankkiserver.external.openfeign.login.dto.SocialInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
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
            return appleTokenResponse.refreshToken();
        } catch (Exception e) {
            throw new BadRequestException(AuthErrorCode.FAILED_TO_LOAD_PRIVATE_KEY);
        }
    }

    public void requestRevoke(final String refreshToken, final String clientSecret) {
        appleFeignClient.revoke(refreshToken, clientId, clientSecret, "refresh_token");
    }
}
