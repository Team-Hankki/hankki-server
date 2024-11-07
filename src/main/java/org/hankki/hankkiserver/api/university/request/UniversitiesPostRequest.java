package org.hankki.hankkiserver.api.university.request;

import java.util.List;

public record UniversitiesPostRequest(List<UniversityRequest> universityRequests) {
  public record UniversityRequest(String name, double longitude, double latitude) { }
}