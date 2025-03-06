package org.hankki.hankkiserver.api.favorite.service;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.user.service.UserInfoFinder;
import org.hankki.hankkiserver.api.favorite.service.command.FavoriteOwnershipGetCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesGetCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesWithStatusGetCommand;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteSharedGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteUserNicknameGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteOwnershipGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoritesWithStatusGetResponse;
import org.hankki.hankkiserver.api.store.service.StoreFinder;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
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
  private final UserInfoFinder userInfoFinder;

  @Transactional(readOnly = true)
  public FavoriteGetResponse findFavorite(final FavoritesGetCommand command) {
    Favorite favorite = favoriteFinder.findByIdWithFavoriteStore(command.favoriteId());
    return FavoriteGetResponse.of(favorite, findStoresInFavorite(favorite));
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

  @Transactional(readOnly = true)
  public FavoriteSharedGetResponse findSharedFavorite(final long id) {
    Favorite favorite = favoriteFinder.findByIdWithFavoriteStore(id);
    return FavoriteSharedGetResponse.of(favorite, getNicknameByOwnerId(getOwnerIdById(id)), findStoresInFavorite(favorite));
  }

  private boolean isStoreAlreadyAdded(final List<FavoriteStore> favoriteStore, final Long commandStoreId) {
    return favoriteStore.stream()
        .anyMatch(f -> f.getStore().getId().equals(commandStoreId));
  }

  private List<Store> findStoresInFavorite(final Favorite favorite){
    if (favoriteHasNoStore(favorite)) {
      return new ArrayList<>();
    }
    return storeFinder.findAllByFavoriteStoresAndDeletedIsFalseOrderByFavoriteStoreId(favorite.getFavoriteStores());
  }

  private boolean favoriteHasNoStore(final Favorite favorite) {
    return favorite.getFavoriteStores().isEmpty();
  }

  @Transactional(readOnly = true)
  public FavoriteOwnershipGetResponse checkFavoriteOwnership(final FavoriteOwnershipGetCommand command) {
     return FavoriteOwnershipGetResponse.of(isOwner(getOwnerIdById(command.favoriteId()), command.userId()));
  }

  private long getOwnerIdById(final long id) {
    return favoriteFinder.findById(id).getUser().getId();
  }

  private boolean isOwner(final long ownerId, final long userId) {
    return ownerId == userId;
  }

  @Transactional(readOnly = true)
  public FavoriteUserNicknameGetResponse getFavoriteUserNickname(final long id) {
    return FavoriteUserNicknameGetResponse.of(getNicknameByOwnerId(getOwnerIdById(id)));
  }

  private String getNicknameByOwnerId(final long userId) {
    return userInfoFinder.getUserInfo(userId).getNickname();
  }
}
