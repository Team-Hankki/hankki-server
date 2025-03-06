package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdater {

    private final UserRepository userRepository;

    public void saveUser(final User user) {
        userRepository.save(user);
    }
}
