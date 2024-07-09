package org.hankki.hankkiserver.api.favorite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteDeleteRequest;
import org.hankki.hankkiserver.api.favorite.service.FavoriteCommandService;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FavoriteController {

  private final FavoriteCommandService favoriteCommandService;

  @PostMapping("/favorites")
  public HankkiResponse<Void> createFavorite(@UserId final Long userId, @RequestBody @Valid final FavoritePostRequest favoritePostRequest) {

    favoriteCommandService.create(FavoritePostCommand.of(userId, favoritePostRequest));
    return HankkiResponse.success(CommonSuccessCode.CREATED);
  }

  @PostMapping("/favorites/delete")
  public HankkiResponse<Void> deleteFavorite(@RequestBody final FavoriteDeleteRequest favoriteDeleteRequest) {

    favoriteCommandService.deleteFavorites(favoriteDeleteRequest);
    return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
  }
}