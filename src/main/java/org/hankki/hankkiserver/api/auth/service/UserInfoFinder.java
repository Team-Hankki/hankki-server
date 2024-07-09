package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.UserErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.repository.UserInfoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoFinder {

    private final UserInfoRepository userInfoRepository;

    public UserInfo getUserInfo(final long userId) {
        return userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(UserErrorCode.USER_INFO_NOT_FOUND));
    }
}
