package org.hankki.hankkiserver.api.user.service.command;

import org.hankki.hankkiserver.api.user.controller.request.UserUniversityPostRequest;
import org.hankki.hankkiserver.domain.user.model.UserUniversity;

public record UserUniversityPostCommand(Long userId, Long universityId, String universityName,
                                        double longitude, double latitude) {
    public UserUniversityPostCommand(Long userId, UserUniversityPostRequest request) {
        this(userId, request.universityId(), request.name(), request.longitude(), request.latitude());
    }

    public UserUniversity toEntity() {
        return UserUniversity.builder()
            .userId(userId)
            .universityId(universityId)
            .universityName(universityName)
            .longitude(longitude)
            .latitude(latitude)
            .build();
    }
}

