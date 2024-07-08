package org.hankki.hankkiserver.api.favorite.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.ErrorCode;
import org.hankki.hankkiserver.common.exception.EntityNotFoundException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteAdpater {

  private final FavoriteRepository favoriteRepository;

  public Favorite findById(final long id) {

    return favoriteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.FAVORITE_NOT_FOUND));

  }
}
