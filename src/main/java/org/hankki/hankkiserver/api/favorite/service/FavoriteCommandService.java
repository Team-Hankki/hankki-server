package org.hankki.hankkiserver.api.favorite.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesDeleteCommand;
import org.hankki.hankkiserver.api.favoritestore.service.FavoriteStoreDeleter;
import org.hankki.hankkiserver.common.code.UserErrorCode;
import org.hankki.hankkiserver.common.exception.UnauthorizedException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCommandService {

  private final FavoriteRepository favoriteRepository;
  private final UserFinder userFinder;
  private final FavoriteFinder favoriteFinder;
  private final FavoriteStoreDeleter favoriteStoreDeleter;
  private final FavoriteDeleter favoriteDeleter;

  @Transactional
  public void create(final FavoritePostCommand command) {

    User findUser = userFinder.getUser(command.userId());

    String title = command.title();
    String details = String.join(" ", command.details());

    favoriteRepository.save(Favorite.create(findUser, title, details));

  }

  @Transactional
  public void deleteFavorites(final FavoritesDeleteCommand command) {

    List<Favorite> favorites = command.favoriteIds().stream()
        .map( favoriteId -> {
            Favorite favorite = favoriteFinder.findById(favoriteId);
            if (!favorite.getUser().getId().equals(command.userId())) {
              throw new UnauthorizedException(UserErrorCode.USER_FORBIDDEN);
            }
            return favorite;
        }).toList();

    favoriteStoreDeleter.deleteAllByFavorites(favorites);
    favoriteDeleter.deleteAll(favorites);

  }
}