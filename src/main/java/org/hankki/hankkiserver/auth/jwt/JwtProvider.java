package org.hankki.hankkiserver.auth.jwt;

import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtGenerator jwtGenerator;

    public Token issueToken(Long userId) {
        return Token.of(jwtGenerator.generateToken(userId, true),
                jwtGenerator.generateToken(userId, false));
    }

    public Long getSubject(String token) {
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        return Long.valueOf(jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject());
    }
}

