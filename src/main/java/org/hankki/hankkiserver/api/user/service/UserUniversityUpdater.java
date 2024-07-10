package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.model.UserUniversity;
import org.hankki.hankkiserver.domain.user.repository.UserUniversityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUniversityUpdater {

    private final UserUniversityRepository userUniversityRepository;

    protected void save(UserUniversity userUniversity) {
        userUniversityRepository.save(userUniversity).getUserId();
    }
}
