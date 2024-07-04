package org.hankki.hankkiserver.oauth.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.exception.InvalidValueException;
import org.hankki.hankkiserver.oauth.apple.dto.ApplePublicKeys;
import org.hankki.hankkiserver.oauth.apple.dto.AppleRevokeRequest;
import org.hankki.hankkiserver.oauth.apple.dto.AppleTokenRequest;
import org.hankki.hankkiserver.oauth.apple.dto.AppleTokenResponse;
import org.hankki.hankkiserver.oauth.dto.SocialInfoDto;
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
    private final AppleClientSecretGenerator appleClientSecretGenerator;
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

    public String getAppleToken(final String code) {
        try {
            String clientSecret = appleClientSecretGenerator.generateClientSecret();
            AppleTokenResponse appleTokenResponse = appleFeignClient.getAppleTokens(
                     AppleTokenRequest.of(code, clientId, clientSecret));
            return appleTokenResponse.refreshToken();
        } catch (Exception e) {
            throw new InvalidValueException(ErrorMessage.FAILED_TO_LOAD_PRIVATE_KEY);
        }
    }

    public void requestRevoke(final String refreshToken) {
        String clientSecret = appleClientSecretGenerator.generateClientSecret();
        AppleRevokeRequest appleRevokeRequest = AppleRevokeRequest.of(
                refreshToken, clientId, clientSecret);
        appleFeignClient.revoke(appleRevokeRequest);
    }
}
