package org.hankki.hankkiserver.api.university.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.UniversityErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.university.repository.UniversityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UniversityFinder {

    private final UniversityRepository universityRepository;

    public University findById(final Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UniversityErrorCode.UNIVERSITY_NOT_FOUND));
    }

    public List<University> findAllByOrderByName () {
        return universityRepository.findAllByOrderByName();
    }
}
