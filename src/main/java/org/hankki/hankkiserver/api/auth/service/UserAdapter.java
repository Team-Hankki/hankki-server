package org.hankki.hankkiserver.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.exception.EntityNotFoundException;
import org.hankki.hankkiserver.domain.user.model.Platform;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;
import org.springframework.stereotype.Component;

import static org.hankki.hankkiserver.domain.user.model.MemberStatus.ACTIVE;

@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserRepository userRepository;

    public User getUser(final Platform platform, final String serialId) {
        return userRepository.findByPlatformAndSerialIdAndMemberStatus(platform, serialId, ACTIVE)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUser(final long userId) {
        return userRepository.findByIdAndMemberStatus(userId, ACTIVE)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public boolean isRegisteredUser(final Platform platform, final SocialInfoDto socialInfo) {
        return userRepository.existsByPlatformAndSerialIdAndMemberStatus(
                platform,
                socialInfo.serialId(),
                ACTIVE);
    }

    public void saveUser(final User user) {
        userRepository.save(user);
    }
}
