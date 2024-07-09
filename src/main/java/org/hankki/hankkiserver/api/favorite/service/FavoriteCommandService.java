package org.hankki.hankkiserver.api.favorite.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.auth.service.UserFinder;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteDeleteRequest;
import org.hankki.hankkiserver.api.favoritestore.service.FavoriteStoreDeleter;
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
  public void create(final FavoritePostCommand favoritePostCommand) {

    User findUser = userFinder.getUser(favoritePostCommand.userId());

    String title = favoritePostCommand.title();
    String details = String.join(" ", favoritePostCommand.details());

    favoriteRepository.save(Favorite.create(findUser, title, details));

  }

  @Transactional
  public void deleteFavorites(final FavoriteDeleteRequest favoriteDeleteRequest) {

    List<Favorite> favorites = favoriteDeleteRequest.favoriteIds().stream()
        .map(favoriteFinder::findById)
        .toList();

    favoriteStoreDeleter.deleteAllByFavorites(favorites);
    favoriteDeleter.deleteAll(favorites);

  }
}