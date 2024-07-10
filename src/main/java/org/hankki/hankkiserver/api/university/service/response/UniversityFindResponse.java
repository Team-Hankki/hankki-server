package org.hankki.hankkiserver.api.university.service.response;

import org.hankki.hankkiserver.domain.university.model.University;

public record UniversityFindResponse(long id, String name, double longitude, double latitude) {
    public static UniversityFindResponse of(University university) {
        return new UniversityFindResponse(university.getId(), university.getName(),
                university.getPoint().getLongitude(), university.getPoint().getLatitude());
    }
}
