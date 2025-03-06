package org.hankki.hankkiserver.api.favorite.service.response;

public record FavoriteUserNicknameGetResponse(
    String nickname
) {
  public static FavoriteUserNicknameGetResponse of(final String nickname) {
    return new FavoriteUserNicknameGetResponse(nickname);
  }
}
