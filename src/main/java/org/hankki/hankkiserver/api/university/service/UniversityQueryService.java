package org.hankki.hankkiserver.api.university.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.university.service.response.UniversitiesResponse;
import org.hankki.hankkiserver.api.university.service.response.UniversityFindResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityQueryService {
    
    private final UniversityFinder universityFinder;

    public UniversitiesResponse findAllByOrderByName() {
        return new UniversitiesResponse(universityFinder.findAllByOrderByName()
                .stream().map(UniversityFindResponse::of).toList());
    }
}
