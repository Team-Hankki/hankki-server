package org.hankki.hankkiserver.external.openfeign.login.apple;

import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.hankki.hankkiserver.external.openfeign.login.OAuthProvider;
import org.hankki.hankkiserver.external.openfeign.login.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.external.openfeign.login.apple.dto.AppleTokenResponse;
import org.hankki.hankkiserver.external.openfeign.login.dto.SocialInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleOAuthProvider implements OAuthProvider {

    private final AppleFeignClient appleFeignClient;
    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;;
    private final AppleClientSecretGenerator appleClientSecretGenerator;

    @Value("${oauth.apple.client-id}")
    private String clientId;

    private static final String TOKEN_TYPE = "refresh_token";
    private static final String GRANT_TYPE = "authorization_code";

    @Override
    public SocialInfoDto getUserInfo(final String identityToken, final String name) {
        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKey();
        PublicKey applePublicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys);
        Claims claims = appleIdentityTokenParser.parsePublicKeyAndGetClaims(identityToken, applePublicKey);
        return SocialInfoDto.of(
                claims.get("sub").toString(),
                name,
                claims.get("email").toString());
    }

    @Override
    public void requestRevoke(final String authorizationCode, final String serialId) {
        try {
            String clientSecret = appleClientSecretGenerator.generateClientSecret();
            String refreshToken = getAppleRefreshToken(authorizationCode, clientSecret);
            appleFeignClient.revoke(refreshToken, clientId, clientSecret, TOKEN_TYPE);
        } catch (Exception e) {
            throw new BadRequestException(AuthErrorCode.APPLE_REVOKE_FAILED);
        }
    }

    private String getAppleRefreshToken(final String code, final String clientSecret) {
        try {
            AppleTokenResponse appleTokenResponse = appleFeignClient.getAppleTokens(code, clientId, clientSecret, GRANT_TYPE);
            return appleTokenResponse.refreshToken();
        } catch (Exception e) {
            throw new BadRequestException(AuthErrorCode.FAILED_TO_LOAD_PRIVATE_KEY);
        }
    }
}
