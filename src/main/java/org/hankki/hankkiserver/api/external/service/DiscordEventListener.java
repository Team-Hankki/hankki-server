package org.hankki.hankkiserver.api.external.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.event.store.CreateStoreEvent;
import org.hankki.hankkiserver.event.store.DeleteStoreEvent;
import org.hankki.hankkiserver.event.user.CreateUserEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DiscordEventListener {

    private final DiscordService discordService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendStoreCreationNotice(CreateStoreEvent event) {
        discordService.sendStoreCreationMessage(event.storeName(), event.universityName());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendUserCreateNotice(CreateUserEvent event) {
        discordService.sendUserCreationMessage(event.userId(), event.userName(), event.platform());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendStoreDeleteNotice(DeleteStoreEvent event) {
        discordService.sendStoreDeleteMessage(event.name(), event.userId());
    }
}
