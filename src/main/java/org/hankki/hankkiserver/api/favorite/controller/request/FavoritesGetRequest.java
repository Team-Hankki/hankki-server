package org.hankki.hankkiserver.api.favorite.controller.request;

public record FavoritesGetRequest(
    Long storeId
) {
  public static FavoritesGetRequest of(final Long storeId){
    return new FavoritesGetRequest(storeId);
  }
}
