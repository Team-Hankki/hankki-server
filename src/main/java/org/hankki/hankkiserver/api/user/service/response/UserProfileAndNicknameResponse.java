package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.user.model.UserInfo;

public record UserProfileAndNicknameResponse(
        String nickname,
        String profileImageUrl
) {
    public static UserProfileAndNicknameResponse of(UserInfo userInfo) {
        return new UserProfileAndNicknameResponse(userInfo.getNickname(), userInfo.getProfileImageUrl());
    }
}
