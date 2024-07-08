package org.hankki.hankkiserver.api.favorite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;
import org.hankki.hankkiserver.api.favorite.service.FavoriteAdpater;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCommandService {

  private final FavoriteRepository favoriteRepository;
  private final UserRepository userRepository;
  private final FavoriteAdpater favoriteAdpater;

  @Transactional
  public Long create(final FavoritePostCommand favoritePostCommand) {

    long userId = favoritePostCommand.memberId();
    User findUser = userRepository.findById(userId).get();

    String title = favoritePostCommand.title();
    String details = String.join(" ", favoritePostCommand.details());

    Favorite favorite = Favorite.create(findUser, title, details); // null -> 유저 들어갈 예정
    favoriteRepository.save(favorite);

    return favorite.getId();

  }

  @Transactional
  public void delete(final long favoriteId) {

    Favorite favorite = favoriteAdpater.findById(favoriteId);
    favoriteRepository.delete(favorite);

  }
}