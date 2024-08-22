package org.hankki.hankkiserver.api.user.service.response;

import org.hankki.hankkiserver.domain.user.model.UserUniversity;

public record UserUniversityFindResponse(Long id,
                                         String name,
                                         double longitude,
                                         double latitude)
{
    public static UserUniversityFindResponse of (UserUniversity userUniversity) {
         return new UserUniversityFindResponse(userUniversity.getUniversityId(), userUniversity.getUniversityName(),
                userUniversity.getLongitude(), userUniversity.getLatitude());
    }
}
