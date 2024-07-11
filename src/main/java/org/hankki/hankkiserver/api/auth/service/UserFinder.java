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

import static org.hankki.hankkiserver.domain.user.model.MemberStatus.ACTIVE;

@Component
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository userRepository;

    public User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    public User getUserReference(final Long userId) {
        return userRepository.getReferenceById(userId);
    }

    public boolean isRegisteredUser(final Platform platform, final SocialInfoDto socialInfo) {
        return userRepository.existsByPlatformAndSerialIdAndMemberStatus(
                platform,
                socialInfo.serialId(),
                ACTIVE);
    }

    public Optional<User> findUserByPlatFormAndSeralId(final Platform platform, final String serialId) {
        return userRepository.findByPlatformAndSerialId(platform, serialId);
    }
}
