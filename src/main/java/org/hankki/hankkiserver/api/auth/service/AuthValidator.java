package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.user.service.UserInfoFinder;
import org.hankki.hankkiserver.auth.jwt.JwtValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthValidator {

    private final JwtValidator jwtValidator;
    private final UserInfoFinder userInfoFinder;

    public void validateRefreshToken(final String refreshToken, final Long userId) {
        jwtValidator.validateRefreshToken(refreshToken);
        String storedRefreshToken = userInfoFinder.getUserInfo(userId).getRefreshToken();
        jwtValidator.checkTokenEquality(refreshToken, storedRefreshToken);
    }
}
