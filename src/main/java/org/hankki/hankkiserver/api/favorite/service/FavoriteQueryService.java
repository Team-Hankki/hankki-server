package org.hankki.hankkiserver.api.favorite.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteFindResponse;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteQueryService {

  private final FavoriteFinder favoriteFinder;

  @Transactional(readOnly = true)
  public FavoriteFindResponse findFavorite(final long id) {

    Favorite favorite = favoriteFinder.findById(id);
    List<Store> stores = favorite.getFavoriteStores().stream().map(FavoriteStore::getStore).toList();

    return FavoriteFindResponse.of(favorite, stores);
  }
}
