package org.hankki.hankkiserver.api.user.service.response;

public record UserProfileAndNicknmaeResponse(
        String nickname,
        String profileImageUrl
) {
    public static UserProfileAndNicknmaeResponse of(String nickname, String profileImageUrl) {
        return new UserProfileAndNicknmaeResponse(nickname, profileImageUrl);
    }
}
