package org.hankki.hankkiserver.api.university.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.university.service.command.UniversitiesPostCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UniversityCommandService {

  private final UniversityUpdater universityUpdater;

  @Transactional
  public void createUniversities(UniversitiesPostCommand universitiesPostCommand) {
    universityUpdater.saveAll(universitiesPostCommand.toEntityList());
  }
}