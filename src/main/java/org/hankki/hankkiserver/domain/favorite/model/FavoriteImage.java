package org.hankki.hankkiserver.domain.favorite.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FavoriteImage {

  BASE_IMAGE,
  ONE_TO_FIVE,
  SIX_TO_TEN,
  ELEVEN_AND_ABOVE;

  public static FavoriteImage getType(final int countFavoriteStore) {
    if (countFavoriteStore == 0) {
      return BASE_IMAGE;
    } else if (countFavoriteStore >= 1 && countFavoriteStore <= 5) {
      return ONE_TO_FIVE;
    } else if (countFavoriteStore >= 6 && countFavoriteStore <= 10) {
      return SIX_TO_TEN;
    }
    return ELEVEN_AND_ABOVE;
  }
}
