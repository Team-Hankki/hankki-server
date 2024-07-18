package org.hankki.hankkiserver.api.favorite.service;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesGetCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesWithStatusGetCommand;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoritesWithStatusGetResponse;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteQueryService {

  private final FavoriteFinder favoriteFinder;
  private final StoreFinder storeFinder;

  @Transactional(readOnly = true)
  public FavoriteGetResponse findFavorite(final FavoritesGetCommand command) {
    Favorite favorite = favoriteFinder.findByIdWithFavoriteStore(command.favoriteId());
    List<Store> store = storeFinder.findAllByIdsWhereDeletedIsFalseOrderByCreatedAtDes(favorite.getFavoriteStores().stream().map(fs -> fs.getStore().getId()).toList());
    return FavoriteGetResponse.of(favorite, store);
  }

  @Transactional(readOnly = true)
  public FavoritesWithStatusGetResponse findFavoritesWithStatus(final FavoritesWithStatusGetCommand command) {
    return FavoritesWithStatusGetResponse.of(
        favoriteFinder.findAllByUserId(command.userId()).stream()
            .collect(Collectors.toMap(
                favorite -> favorite,
                favorite -> isStoreAlreadyAdded(favorite.getFavoriteStores(),  command.storeId()),
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new)));
  }

  private boolean isStoreAlreadyAdded(final List<FavoriteStore> favoriteStore, final Long commandStoreId) {
    return favoriteStore.stream()
        .anyMatch(f -> f.getStore().getId().equals(commandStoreId));
  }
}