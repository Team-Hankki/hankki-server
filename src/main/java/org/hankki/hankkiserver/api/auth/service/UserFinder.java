package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.UserErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.hankki.hankkiserver.domain.user.model.UserStatus.ACTIVE;

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

    protected boolean isRegisteredUser(final Platform platform, final SocialInfoDto socialInfo) {
        return userRepository.existsByPlatformAndSerialIdAndStatus(
                platform,
                socialInfo.serialId(),
                ACTIVE);
    }

    protected Optional<User> findUserByPlatFormAndSeralId(final Platform platform, final String serialId) {
        return userRepository.findByPlatformAndSerialId(platform, serialId);
    }
}
