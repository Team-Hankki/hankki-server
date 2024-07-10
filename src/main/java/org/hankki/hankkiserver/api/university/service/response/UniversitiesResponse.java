package org.hankki.hankkiserver.api.university.service.response;

import org.hankki.hankkiserver.domain.university.model.University;

import java.util.List;

public record UniversitiesResponse(List<UniversityFindResponse> universities) {
}
