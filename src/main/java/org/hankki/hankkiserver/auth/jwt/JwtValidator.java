package org.hankki.hankkiserver.auth.jwt;

import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.AuthErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtValidator {

    private final JwtGenerator jwtGenerator;

    public void validateAccessToken(final String accessToken) {
        try {
            String role = parseToken(accessToken).get(JwtGenerator.USER_ROLE_CLAIM_NAME, String.class);
            if (role == null) {
                throw new UnauthorizedException(AuthErrorCode.INVALID_ACCESS_TOKEN_VALUE);
            }
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(AuthErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(AuthErrorCode.INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    public void validateRefreshToken(final String refreshToken) {
        try {
            parseToken(getToken(refreshToken));
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(AuthErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(AuthErrorCode.INVALID_REFRESH_TOKEN_VALUE);
        }
    }

    public void checkTokenEquality(final String refreshToken, final String storedRefreshToken) {
        if (!getToken(refreshToken).equals(storedRefreshToken)) {
            throw new UnauthorizedException(AuthErrorCode.MISMATCH_REFRESH_TOKEN);
        }
    }

    private String getToken(final String refreshToken) {
        if (refreshToken.startsWith(BEARER)) {
            return refreshToken.substring(BEARER.length());
        }
        throw new UnauthorizedException(AuthErrorCode.MISSING_BEARER_PREFIX);
    }

    private Claims parseToken(final String token) {
        return jwtGenerator.getJwtParser().parseClaimsJws(token).getBody();
    }
}
