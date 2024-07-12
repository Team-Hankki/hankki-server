package org.hankki.hankkiserver.api.favorite.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.favorite.service.command.FavoriteGetCommand;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteFindResponse;
import org.hankki.hankkiserver.common.code.UserErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteQueryService {

  private final FavoriteFinder favoriteFinder;

  @Transactional(readOnly = true)
  public FavoriteFindResponse findFavorite(final FavoriteGetCommand command) {
    Favorite favorite = favoriteFinder.findById(command.favoriteId());
    return FavoriteFindResponse.of(favorite, favorite.getFavoriteStores().stream().map(FavoriteStore::getStore).toList());
  }
}
