package org.hankki.hankkiserver.api.favorite.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteIsOwnerGetResponse;
import org.hankki.hankkiserver.common.code.FavoriteErrorCode;
import org.hankki.hankkiserver.common.exception.NotFoundException;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.hankki.hankkiserver.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteFinder {

  private final FavoriteRepository favoriteRepository;

  public Favorite findById(final Long id) {
    return favoriteRepository.findById(id).orElseThrow(() -> new NotFoundException(FavoriteErrorCode.FAVORITE_NOT_FOUND));
  }

  public List<Favorite> findAllByIds(final List<Long> ids) {
    return favoriteRepository.findByIdIn(ids);
  }

  public List<Favorite> findAllByUserId(final Long userId) {
    return favoriteRepository.findByUserIdOrderByCreatedAtDesc(userId);
  }

  protected Favorite findByIdWithUser(final Long id) {
    return favoriteRepository.findByIdWithUser(id).orElseThrow(() -> new NotFoundException(FavoriteErrorCode.FAVORITE_NOT_FOUND));
  }

  protected Favorite findByIdWithFavoriteStore(final Long id) {
    return favoriteRepository.findByIdWithFavoriteStore(id)
        .orElseThrow(() -> new NotFoundException(FavoriteErrorCode.FAVORITE_NOT_FOUND));
  }

  protected Optional<Favorite> findByNameAndUser(final String name, final User user) {
    return favoriteRepository.findByNameAndUser(name, user);
  }

  boolean findByIdAndUserId(final long id, final long userId) {
    return favoriteRepository.existsByIdAndAndUser(id, userId);
  }
}