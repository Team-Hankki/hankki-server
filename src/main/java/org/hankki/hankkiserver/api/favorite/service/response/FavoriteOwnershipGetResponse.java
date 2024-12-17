package org.hankki.hankkiserver.api.favorite.service.response;

public record FavoriteOwnershipGetResponse(
    boolean isOwner
) {
  public static FavoriteOwnershipGetResponse of(final boolean isOwner) {
    return new FavoriteOwnershipGetResponse(isOwner);
  }
}
