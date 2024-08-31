package org.hankki.hankkiserver.api.external.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.event.store.CreateStoreEvent;
import org.hankki.hankkiserver.event.store.DeleteStoreEvent;
import org.hankki.hankkiserver.event.user.CreateUserEvent;
import org.hankki.hankkiserver.external.openfeign.discord.dto.DiscordMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordEventListener {

    private final DiscordService discordService;

    @EventListener
    public void sendStoreCreationNotice(CreateStoreEvent event) {
        discordService.sendStoreCreationMessage(DiscordMessage.storeCreationMessageOf(event.storeName(), event.universityName()));
    }

    @EventListener
    public void sendUserCreateNotice(CreateUserEvent event) {
        discordService.sendUserCreationMessage(DiscordMessage.userCreationMessageOf(event.userId(), event.userName(), event.platform()));
    }

    @EventListener
    public void sendStoreDeleteNotice(DeleteStoreEvent event) {
        discordService.sendStoreDeleteMessage(DiscordMessage.storeDeleteMessageOf(event.name()));
    }
}
