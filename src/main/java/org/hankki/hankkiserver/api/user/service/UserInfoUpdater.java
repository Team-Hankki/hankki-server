package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.repository.UserInfoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoUpdater {

    private final UserInfoRepository userInfoRepository;

    public void saveUserInfo(final UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
