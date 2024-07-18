package org.hankki.hankkiserver.external.openfeign.apple;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AppleClientSecretGenerator {

    private static final String AUDIENCE = "https://appleid.apple.com";
    private final ApplePrivateKeyGenerator applePrivateKeyGenerator;

    @Value("${oauth.apple.key}")
    private String keyId;
    @Value("${oauth.apple.team-id}")
    private String teamId;
    @Value("${oauth.apple.client-id}")
    private String clientId;

    public String generateClientSecret() {
            Date expirationDate = Date.from(LocalDateTime.now().plusDays(5)
                    .atZone(ZoneId.systemDefault()).toInstant());
        try {
            return Jwts.builder()
                    .setHeaderParam("alg", SignatureAlgorithm.ES256)
                    .setHeaderParam("kid", keyId)
                    .setIssuer(teamId)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expirationDate)
                    .setAudience(AUDIENCE)
                    .setSubject(clientId)
                    .signWith(applePrivateKeyGenerator.getPrivateKey(), SignatureAlgorithm.ES256)
                    .compact();
        } catch (Exception e) {
            throw new BadRequestException(AuthErrorCode.FAILED_TO_LOAD_PRIVATE_KEY);
        }
    }
}
