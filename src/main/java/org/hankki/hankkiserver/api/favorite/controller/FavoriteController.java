package org.hankki.hankkiserver.api.favorite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteDeleteRequest;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritesGetRequest;
import org.hankki.hankkiserver.api.favorite.service.FavoriteCommandService;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesGetCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;
import org.hankki.hankkiserver.api.favorite.service.command.FavoriteStoreDeleteCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoriteStorePostCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesDeleteCommand;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritesWithStatusGetCommand;
import org.hankki.hankkiserver.api.favorite.service.response.FavoritesWithStatusGetResponse;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.hankki.hankkiserver.api.favorite.service.FavoriteQueryService;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteGetResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping("/favorites/{favoriteId}/stores/{storeId}")
  public HankkiResponse<Void> createFavoriteStore(
      @UserId final Long userId,
      @PathVariable("favoriteId") final Long favoriteId,
      @PathVariable("storeId") final Long storeId
  ) {
    favoriteCommandService.createFavoriteStore(FavoriteStorePostCommand.of(userId, favoriteId, storeId));
    return HankkiResponse.success(CommonSuccessCode.CREATED);
  }

  @DeleteMapping("/favorites/{favoriteId}/stores/{storeId}")
  public HankkiResponse<Void> deleteFavoriteStore(
      @UserId final Long userId,
      @PathVariable("favoriteId") final Long favoriteId,
      @PathVariable("storeId") final Long storeId
  ) {
    favoriteCommandService.deleteFavoriteStore(
        FavoriteStoreDeleteCommand.of(userId, favoriteId, storeId));
    return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
  }

  @GetMapping("/favorites/{favoriteId}")
  public HankkiResponse<FavoriteGetResponse> getFavorite(@UserId final Long userId, @PathVariable(name = "favoriteId") final Long favoriteId) {
    return HankkiResponse.success(CommonSuccessCode.OK, favoriteQueryService.findFavorite(
        FavoritesGetCommand.of(userId, favoriteId)));
  }

  @GetMapping("/favorites")
  public HankkiResponse<FavoritesWithStatusGetResponse> getFavoritesWithStatus(@UserId Long id, @RequestBody final FavoritesGetRequest request) {
    return HankkiResponse.success(CommonSuccessCode.OK, favoriteQueryService.findFavoritesWithStatus(FavoritesWithStatusGetCommand.of(id, request)));
  }
}