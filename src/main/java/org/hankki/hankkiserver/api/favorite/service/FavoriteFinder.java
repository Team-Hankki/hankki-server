package org.hankki.hankkiserver.api.favorite.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.FavoriteErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteFinder {

  private final FavoriteRepository favoriteRepository;

  public Favorite findById(final long id) {
    return favoriteRepository.findById(id).orElseThrow(() -> new NotFoundException(FavoriteErrorCode.FAVORITE_NOT_FOUND));
  }
}
