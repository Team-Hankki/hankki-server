package org.hankki.hankkiserver.api.favorite.service.response;

public record FavoriteIsOwnerGetResponse(
    boolean isOwner
) {
  public static FavoriteIsOwnerGetResponse of(final boolean isOwner) {
    return new FavoriteIsOwnerGetResponse(isOwner);
  }
}
