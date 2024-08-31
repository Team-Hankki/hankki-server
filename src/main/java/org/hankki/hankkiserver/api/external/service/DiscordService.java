package org.hankki.hankkiserver.api.external.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.external.openfeign.discord.DiscordFeignClient;
import org.hankki.hankkiserver.external.openfeign.discord.dto.DiscordMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscordService {

    private final DiscordFeignClient discordFeignClient;

    public void sendStoreCreationMessage(DiscordMessage message) {
        discordFeignClient.sendStoreCreationMessage(message);
    }
    public void sendUserCreationMessage(DiscordMessage message) {
        discordFeignClient.sendUserCreationMessage(message);
    }
    public void sendStoreDeleteMessage(DiscordMessage message) {
        discordFeignClient.sendStoreDeleteMessage(message);
    }
}
