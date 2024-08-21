package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.user.model.UserInfo;

public record UserNicknameResponse(
        String nickname
) {
    public static UserNicknameResponse of(UserInfo userInfo) {
        return new UserNicknameResponse(userInfo.getNickname());
    }
}
