package org.hankki.hankkiserver.event.user;

public record CreateUserEvent(
    Long userId,
    String userName,
    String platform
) {
    public static CreateUserEvent of(Long userId, String userName, String platform) {
        return new CreateUserEvent(userId, userName, platform);
    }
}
