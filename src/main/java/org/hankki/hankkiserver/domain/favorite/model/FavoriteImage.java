package org.hankki.hankkiserver.domain.favorite.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FavoriteImage {

  SHORT,
  TALL,
  GRANDE,
  VENTI;

  public static FavoriteImage getType(final int countFavoriteStore) {
    if (countFavoriteStore == 0) {
      return SHORT;
    } else if (countFavoriteStore >= 1 && countFavoriteStore <= 5) {
      return TALL;
    } else if (countFavoriteStore >= 6 && countFavoriteStore <= 10) {
      return GRANDE;
    }
    return VENTI;
  }
}
