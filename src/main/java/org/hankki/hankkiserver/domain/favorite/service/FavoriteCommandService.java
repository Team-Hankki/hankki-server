package org.hankki.hankkiserver.domain.favorite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.favorite.repository.FavoriteRepository;
import org.hankki.hankkiserver.domain.favorite.service.dto.command.FavoritePostCommandDto;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hankki.hankkiserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteCommandService {

  private final FavoriteRepository favoriteRepository;
  private final UserRepository userRepository;

  @Transactional
  public String create(final FavoritePostCommandDto favoritePostCommandDto) {

    // 유저찾기 로직 - 로그인 로직 머지되면 수정
    User findUser = userRepository.findById(1L).orElseThrow();

    String title = favoritePostCommandDto.title();
    String details = String.join(" ", favoritePostCommandDto.details());

    Favorite newFavorite = Favorite.create(findUser, title, details); // null -> 유저 들어갈 예정
    favoriteRepository.save(newFavorite);

    return newFavorite.getId().toString();
  }


}
