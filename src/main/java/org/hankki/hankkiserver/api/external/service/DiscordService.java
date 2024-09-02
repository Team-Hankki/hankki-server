package org.hankki.hankkiserver.api.external.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.external.openfeign.discord.DiscordFeignClient;
import org.hankki.hankkiserver.external.openfeign.discord.dto.DiscordMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscordService {

    private final DiscordFeignClient discordFeignClient;

    public void sendStoreCreationMessage(String storeName, String universityName) {
        discordFeignClient.sendStoreCreationMessage(DiscordMessage.storeCreationMessageOf(storeName, universityName));
    }
    public void sendUserCreationMessage(Long userId, String userName, String platform) {
        discordFeignClient.sendUserCreationMessage(DiscordMessage.userCreationMessageOf(userId, userName, platform));
    }
    public void sendStoreDeleteMessage(String storeName, Long userId) {
        discordFeignClient.sendStoreDeleteMessage(DiscordMessage.storeDeleteMessageOf(storeName, userId));
    }
}
