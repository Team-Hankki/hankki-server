package org.hankki.hankkiserver.api.service.favorite;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.hankki.hankkiserver.api.service.favorite.command.FavoritePostCommand;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCommandService {

  private final FavoriteRepository favoriteRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long create(final FavoritePostCommand favoritePostCommand) {

    User findUser = userRepository.findById(1L).get();

    String title = favoritePostCommand.title();
    String details = String.join(" ", favoritePostCommand.details());

    Favorite favorite = Favorite.create(findUser, title, details); // null -> 유저 들어갈 예정
    favoriteRepository.save(favorite);

    return favorite.getId();

  }
}