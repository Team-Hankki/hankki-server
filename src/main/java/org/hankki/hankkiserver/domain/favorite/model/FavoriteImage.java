package org.hankki.hankkiserver.domain.favorite.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;

@Getter
@RequiredArgsConstructor
public enum FavoriteImage {

  BASE_IMAGE("default.com"),
  ONE_TO_FIVE_IMAGE("1.com"),
  SIX_TO_TEN_IMAGE("2.com"),
  ELEVEN_AND_ABOVE_IMAGE("3.com");

  private final String url;

  public static String getUrl(final int countFavoriteStore) {
    if (countFavoriteStore == 0) {
      return BASE_IMAGE.getUrl();
    } else if (countFavoriteStore >= 1 && countFavoriteStore <= 5) {
      return ONE_TO_FIVE_IMAGE.getUrl();
    } else if (countFavoriteStore >= 6 && countFavoriteStore <= 10) {
      return SIX_TO_TEN_IMAGE.getUrl();
    }
    return ELEVEN_AND_ABOVE_IMAGE.getUrl();
  }
}
