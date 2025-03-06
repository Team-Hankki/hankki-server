package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.user.repository.UserUniversityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUniversityDeleter {

    private final UserUniversityRepository userUniversityRepository;

    protected void deleteById(Long id) {
        userUniversityRepository.deleteById(id);
    }
}
