package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.repository.UserInfoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoDeleter {

    private final UserInfoRepository userInfoRepository;

    public void softDelete(final long userId) {
        userInfoRepository.softDeleteByUserId(userId);
    }
}
