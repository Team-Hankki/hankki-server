package org.hankki.hankkiserver.external.openfeign.discord.dto;

public record DiscordMessage(String content) {
    public static DiscordMessage storeCreationMessageOf(String storeName, String universityName) {
        return new DiscordMessage(universityName+ "에 <" +storeName+ ">가 생성되었습니다.");
    }

    public static DiscordMessage userCreationMessageOf(Long userId, String userName, String platform) {
        return new DiscordMessage( platform + userId + "번째 유저 <" +userName+ ">가 가입했습니다.");
    }

    public static DiscordMessage storeDeleteMessageOf(String universityName) {
        return new DiscordMessage("<" +universityName+ ">가 삭제되었습니다.");
    }
}
