package org.hankki.hankkiserver.api.university.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.university.repository.UniversityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UniversityFinder {

    private final UniversityRepository universityRepository;

    public List<University> findAll() {
        return universityRepository.findAll();
    }
}