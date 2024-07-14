package org.hankki.hankkiserver.domain.favorite.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;

@Getter
@RequiredArgsConstructor
public enum FavoriteImage {

  BASE_IMAGE,
  ONE_TO_FIVE_IMAGE,
  SIX_TO_TEN_IMAGE,
  ELEVEN_AND_ABOVE_IMAGE;

  public static FavoriteImage getType(final int countFavoriteStore) {
    if (countFavoriteStore == 0) {
      return BASE_IMAGE;
    } else if (countFavoriteStore >= 1 && countFavoriteStore <= 5) {
      return ONE_TO_FIVE_IMAGE;
    } else if (countFavoriteStore >= 6 && countFavoriteStore <= 10) {
      return SIX_TO_TEN_IMAGE;
    }
    return ELEVEN_AND_ABOVE_IMAGE;
  }
}
