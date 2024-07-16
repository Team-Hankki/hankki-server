package org.hankki.hankkiserver.api.favorite.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteUpdater {

  private final FavoriteRepository favoriteRepository;

  protected Long save(final Favorite favorite) {
    return favoriteRepository.save(favorite).getId();
  }
}
