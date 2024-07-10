package org.hankki.hankkiserver.api.university.service.response;

import java.util.List;

public record UniversitiesResponse(List<UniversityFindResponse> universities) {
}
