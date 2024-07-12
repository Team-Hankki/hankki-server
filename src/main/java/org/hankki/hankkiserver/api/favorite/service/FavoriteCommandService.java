package org.hankki.hankkiserver.api.favorite.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.favorite.service.command.FavoriteStoreDeleteCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoriteStorePostCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesDeleteCommand;
import org.hankki.hankkiserver.api.favoritestore.service.FavoriteStoreDeleter;
import org.hankki.hankkiserver.api.favoritestore.service.FavoriteStoreFinder;
import org.hankki.hankkiserver.api.favoritestore.service.FavoriteStoreUpdater;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.common.code.FavoriteStoreErrorCode;
import org.hankki.hankkiserver.common.code.UserErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCommandService {

  private final UserFinder userFinder;
  private final FavoriteFinder favoriteFinder;
  private final StoreFinder storeFinder;
  private final FavoriteUpdater favoriteUpdater;
  private final FavoriteStoreUpdater favoriteStoreUpdater;
  private final FavoriteStoreDeleter favoriteStoreDeleter;
  private final FavoriteStoreFinder favoriteStoreFinder;
  private final FavoriteDeleter favoriteDeleter;

  @Transactional
  public Long create(final FavoritePostCommand command) {

    User findUser = userFinder.getUser(command.userId());
    String title = command.title();
    String details = String.join(" ", command.details());

    return favoriteUpdater.save(Favorite.create(findUser, title, details));
  }

  @Transactional
  public void deleteFavorites(final FavoritesDeleteCommand command) {

    List<Favorite> favorites = favoriteFinder.findAllByIds(command.favoriteIds());

    favorites.forEach(favorite -> {
      validateUserAuthorization(userFinder.getUser(command.userId()), favorite.getUser());
    });

    favoriteStoreDeleter.deleteAllByFavorites(favorites);
    favoriteDeleter.deleteAll(favorites);
  }

  @Transactional
  public Long createFavoriteStore(final FavoriteStorePostCommand command) {

    Favorite favorite = favoriteFinder.findById(command.favoriteId());
    Store store = storeFinder.findByIdWhereDeletedIsFalse(command.storeId());

    validateUserAuthorization(userFinder.getUser(command.userId()), favorite.getUser());

    FavoriteStore favoriteStore = favoriteStoreUpdater.save(FavoriteStore.create(store, favorite));
    favorite.updateImageByFavoriteStoreCount(favoriteStoreFinder.countByFavorite(favorite));

    return favoriteStore.getId();
  }

  @Transactional
  public void deleteFavoriteStore(final FavoriteStoreDeleteCommand command) {

    Favorite favorite = favoriteFinder.findById(command.favoriteId());

    validateUserAuthorization(userFinder.getUser(command.userId()), favorite.getUser());

    favoriteStoreDeleter.delete(favoriteStoreFinder.findByFavoriteIdAndStoreId(favorite.getId(), command.storeId()));
    favorite.updateImageByFavoriteStoreCount(favoriteStoreFinder.countByFavorite(favorite));
  }

  private void validateUserAuthorization(User user, User commandUser) {
    if (!user.equals(commandUser)) {
      throw new UnauthorizedException(UserErrorCode.USER_FORBIDDEN);
    }
  }
}