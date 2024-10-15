package org.hankki.hankkiserver.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisherAdapter implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(Object event) {
        applicationEventPublisher.publishEvent(event);
    }
}
