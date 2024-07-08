package org.hankki.hankkiserver.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.dto.ErrorMessage;
import org.hankki.hankkiserver.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import static org.hankki.hankkiserver.auth.filter.JwtAuthenticationFilter.BEARER;

@RequiredArgsConstructor
@Component
public class JwtValidator {

    private final JwtGenerator jwtGenerator;

    public void validateAccessToken(String accessToken) {
        try {
            parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorMessage.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorMessage.INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    public void validateRefreshToken(final String refreshToken) {
        try {
            parseToken(getToken(refreshToken));
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorMessage.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorMessage.INVALID_REFRESH_TOKEN_VALUE);
        }
    }

    public void equalsRefreshToken(
            final String refreshToken,
            final String storedRefreshToken) {
        if (!getToken(refreshToken).equals(storedRefreshToken)) {
            throw new UnauthorizedException(ErrorMessage.MISMATCH_REFRESH_TOKEN);
        }
    }

    private void parseToken(String token) {
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        jwtParser.parseClaimsJws(token);
    }

    private String getToken(final String refreshToken) {
        if (refreshToken.startsWith(BEARER)) {
            return refreshToken.substring(BEARER.length());
        }
        throw new UnauthorizedException(ErrorMessage.INVALID_ACCESS_TOKEN);
    }
}
