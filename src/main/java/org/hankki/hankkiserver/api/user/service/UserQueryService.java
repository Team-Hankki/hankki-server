package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserInfoFinder;
import org.hankki.hankkiserver.api.user.service.response.UserProfileAndNicknameResponse;
import org.hankki.hankkiserver.api.user.service.response.UserUniversityFindResponse;
import org.hankki.hankkiserver.common.code.UserUniversityErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
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

    @Transactional(readOnly = true)
    public UserProfileAndNicknameResponse getUserProfileAndNickname(final Long userId) {
        return UserProfileAndNicknameResponse.of(userInfoFinder.getUserInfo(userId));
    }
}
