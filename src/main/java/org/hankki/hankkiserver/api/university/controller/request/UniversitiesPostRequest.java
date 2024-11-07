package org.hankki.hankkiserver.api.university.controller.request;

import java.util.List;

public record UniversitiesPostRequest(List<UniversityRequest> universities) {
  public record UniversityRequest(String name, double longitude, double latitude) { }
}