package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.auth.service.UserInfoFinder;
import org.hankki.hankkiserver.api.user.service.response.UserProfileAndNicknmaeResponse;
import org.hankki.hankkiserver.api.user.service.response.UserUniversityFindResponse;
import org.hankki.hankkiserver.common.code.UserUniversityErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.user.model.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserUniversityFinder userUniversityFinder;
    private final UserInfoFinder userInfoFinder;

    @Transactional(readOnly = true)
    public UserUniversityFindResponse findUserUniversity(Long userId) {
        return UserUniversityFindResponse.of(userUniversityFinder.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(UserUniversityErrorCode.USER_UNIVERSITY_NOT_FOUND)));
    }

    public UserProfileAndNicknmaeResponse getUserProfileAndNickname(final Long userId) {
        UserInfo userInfo = userInfoFinder.getUserInfo(userId);
        return UserProfileAndNicknmaeResponse.of(userInfo.getNickname(), userInfo.getProfileImageUrl());
    }
}
