package org.hankki.hankkiserver.external.openfeign.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

@Component
public class AppleIdentityTokenParser {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Map<String, String> parseHeaders(String identityToken) {
        try {
            String encodedHeader = identityToken.split("\\.")[0];
            String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));
            return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_APPLE_IDENTITY_TOKEN);
        }
    }

    public Claims parsePublicKeyAndGetClaims(String identityToken, PublicKey publicKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(identityToken)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_IDENTITY_TOKEN);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_IDENTITY_TOKEN_VALUE);
        }
    }
}
