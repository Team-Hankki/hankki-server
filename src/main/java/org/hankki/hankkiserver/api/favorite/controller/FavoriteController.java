package org.hankki.hankkiserver.api.favorite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteDeleteRequest;
import org.hankki.hankkiserver.api.favorite.service.FavoriteCommandService;
import org.hankki.hankkiserver.api.favorite.service.command.FavoriteGetCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesDeleteCommand;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.hankki.hankkiserver.api.favorite.service.FavoriteQueryService;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteFindResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FavoriteController {

  private final FavoriteCommandService favoriteCommandService;
  private final FavoriteQueryService favoriteQueryService;

  @PostMapping("/favorites")
  public HankkiResponse<Void> createFavorite(@UserId final Long userId, @RequestBody @Valid final FavoritePostRequest request) {

    favoriteCommandService.create(FavoritePostCommand.of(userId, request));
    return HankkiResponse.success(CommonSuccessCode.CREATED);
  }

  @PostMapping("/favorites/batch-delete")
  public HankkiResponse<Void> deleteFavorite(@UserId final Long userId, @RequestBody final FavoriteDeleteRequest request) {

    favoriteCommandService.deleteFavorites(FavoritesDeleteCommand.of(userId, request));
    return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
  }

  @GetMapping("/favorites/{favoriteId}")
  public HankkiResponse<FavoriteFindResponse> getFavorite(@UserId final Long userId, @PathVariable(name = "favoriteId") final Long favoriteId) {
    return HankkiResponse.success(CommonSuccessCode.OK, favoriteQueryService.findFavorite(FavoriteGetCommand.of(userId, favoriteId)));
  }
}