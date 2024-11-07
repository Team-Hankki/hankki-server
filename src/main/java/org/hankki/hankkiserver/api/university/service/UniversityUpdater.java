package org.hankki.hankkiserver.api.university.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.university.repository.UniversityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityUpdater {

  private final UniversityRepository universityRepository;

  void saveAll(List<University> universities){
    universityRepository.saveAll(universities);
  }
}