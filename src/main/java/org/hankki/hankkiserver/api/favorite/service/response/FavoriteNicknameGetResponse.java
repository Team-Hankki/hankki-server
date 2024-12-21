package org.hankki.hankkiserver.api.favorite.service.response;

public record FavoriteNicknameGetResponse(
    String nickname
) {
  public static FavoriteNicknameGetResponse of(final String nickname) {
    return new FavoriteNicknameGetResponse(nickname);
  }
}
