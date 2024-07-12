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
  private final UserFinder userFinder;

  @Transactional(readOnly = true)
  public FavoriteFindResponse findFavorite(final FavoriteGetCommand command) {
    Favorite favorite = favoriteFinder.findById(command.favoriteId());
    validateUserAuthorization(userFinder.getUser(command.userId()), favorite.getUser());
    return FavoriteFindResponse.of(favorite, favorite.getFavoriteStores().stream().map(FavoriteStore::getStore).toList());
  }

  private void validateUserAuthorization(User user, User commandUser) {
    if (!user.equals(commandUser)) {
      throw new UnauthorizedException(UserErrorCode.USER_FORBIDDEN);
    }
  }
}
