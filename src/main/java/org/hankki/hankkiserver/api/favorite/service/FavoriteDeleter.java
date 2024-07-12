package org.hankki.hankkiserver.api.favorite.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.common.code.BusinessErrorCode;
import org.hankki.hankkiserver.common.exception.InternalServerException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteDeleter {

  private final FavoriteRepository favoriteRepository;

  protected void deleteAll(final List<Favorite> favorites) {
    favoriteRepository.deleteAll(favorites);
  }
}
