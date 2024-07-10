package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.UserUniversity;
import org.hankki.hankkiserver.domain.user.repository.UserUniversityRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserUniversityFinder {

    private final UserUniversityRepository userUniversityRepository;

    public Optional<UserUniversity> findByUserId(Long userId) {
        return userUniversityRepository.findByUserId(userId);
    }
}
