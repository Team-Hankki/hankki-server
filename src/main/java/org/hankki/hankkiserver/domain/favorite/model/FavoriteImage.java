package org.hankki.hankkiserver.domain.favorite.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FavoriteImage {

  TYPE_ONE,
  TYPE_TWO,
  TYPE_THREE,
  TYPE_FOUR;

  public static FavoriteImage getType(final int countFavoriteStore) {
    if (countFavoriteStore == 0) {
      return TYPE_ONE;
    } else if (countFavoriteStore >= 1 && countFavoriteStore <= 5) {
      return TYPE_TWO;
    } else if (countFavoriteStore >= 6 && countFavoriteStore <= 10) {
      return TYPE_THREE;
    }
    return TYPE_FOUR;
  }
}