package org.hankki.hankkiserver.external.openfeign.oauth.apple;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    protected String generateClientSecret() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Date expirationDate = Date.from(LocalDateTime.now().plusMinutes(5)
                .atZone(ZoneId.systemDefault()).toInstant());
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
    }
}
