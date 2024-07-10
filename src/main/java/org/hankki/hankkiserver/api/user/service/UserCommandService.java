package org.hankki.hankkiserver.api.user.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.user.service.command.UserUniversityPostCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserUniversityUpdater userUniversityUpdater;
    private final UserUniversityDeleter userUniversityDeleter;
    private final UserUniversityFinder userUniversityFinder;

    @Transactional
    public void saveUserUniversity(UserUniversityPostCommand userUniversityPostCommand) {
        userUniversityFinder.findByUserId(userUniversityPostCommand.userId())
                        .ifPresent(userUniversity -> userUniversityDeleter.deleteById(userUniversity.getId()));
        userUniversityUpdater.save(userUniversityPostCommand.toEntity());
    }
}
