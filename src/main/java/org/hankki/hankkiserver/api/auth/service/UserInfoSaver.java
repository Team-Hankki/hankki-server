package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.hankki.hankkiserver.domain.user.repository.UserInfoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoSaver {

    private final UserInfoRepository userInfoRepository;

    public void saveUserInfo(final UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
