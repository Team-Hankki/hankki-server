package org.hankki.hankkiserver.domain.favorite.service.dto.command;

import java.util.List;
import org.hankki.hankkiserver.domain.favorite.service.dto.request.FavoritePostDto;

public record FavoritePostCommandDto (
    long memberId,
    String title,
    List<String> details
) {

  public static FavoritePostCommandDto of(final long memberId, final FavoritePostDto favoritePostDto) {
    return new FavoritePostCommandDto(
        memberId,
        favoritePostDto.title(),
        favoritePostDto.details()
        );
  }
}
