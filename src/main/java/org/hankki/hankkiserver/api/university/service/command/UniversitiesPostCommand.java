package org.hankki.hankkiserver.api.university.service.command;

import java.util.List;
import org.hankki.hankkiserver.api.university.request.UniversitiesPostRequest;
import org.hankki.hankkiserver.domain.common.Point;
import org.hankki.hankkiserver.domain.university.model.University;

public record UniversitiesPostCommand(List<UniversityCommand> universityCommands) {

  record UniversityCommand(String name, double longitude, double latitude) {
    University toEntity() {
      return University.create(name, new Point(latitude, longitude));
    }
  }

  public static UniversitiesPostCommand of(UniversitiesPostRequest universitiesPostRequest) {
    return new UniversitiesPostCommand(universitiesPostRequest.universityRequests().stream().map(it -> new UniversityCommand(it.name(), it.longitude(), it.latitude())).toList());
  }

  public List<University> toEntityList() {
    return universityCommands.stream().map(UniversityCommand::toEntity).toList();
  }
}
