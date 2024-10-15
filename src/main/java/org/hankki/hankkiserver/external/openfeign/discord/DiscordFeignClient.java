package org.hankki.hankkiserver.external.openfeign.discord;

import org.hankki.hankkiserver.external.openfeign.discord.dto.DiscordMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "discordClient", url = "${discord.webhook.url}")
public interface DiscordFeignClient {
    @PostMapping(value = "${discord.webhook.create-store}", produces = MediaType.APPLICATION_JSON_VALUE)
    void sendStoreCreationMessage(@RequestBody DiscordMessage message);

    @PostMapping(value = "${discord.webhook.create-user}", produces = MediaType.APPLICATION_JSON_VALUE)
    void sendUserCreationMessage(@RequestBody DiscordMessage message);

    @PostMapping(value = "${discord.webhook.delete-store}", produces = MediaType.APPLICATION_JSON_VALUE)
    void sendStoreDeleteMessage(@RequestBody DiscordMessage message);
}
