package org.hankki.hankkiserver.api.user.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.UserErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository userRepository;

    public User getUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    public User getUserReference(final Long id) {
        return userRepository.getReferenceById(id);
    }

    public Optional<User> findUserByPlatFormAndSeralId(final Platform platform, final String serialId) {
        return userRepository.findByPlatformAndSerialId(platform, serialId);
    }
}
