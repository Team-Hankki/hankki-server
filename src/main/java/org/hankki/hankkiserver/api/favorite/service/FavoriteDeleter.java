package org.hankki.hankkiserver.api.favorite.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoriteDeleter {

  private final FavoriteRepository favoriteRepository;

  protected void deleteAll(final List<Favorite> favorites) {
    favoriteRepository.deleteAll(favorites);
  }
}
