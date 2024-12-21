package org.hankki.hankkiserver.api.favorite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteDeleteRequest;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteSharedPostRequest;
import org.hankki.hankkiserver.api.favorite.service.FavoriteCommandService;
import org.hankki.hankkiserver.api.favorite.service.FavoriteQueryService;
import org.hankki.hankkiserver.api.favorite.service.command.*;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteUserNicknameGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoriteOwnershipGetResponse;
import org.hankki.hankkiserver.api.favorite.service.response.FavoritesWithStatusGetResponse;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FavoriteController {

  private final FavoriteCommandService favoriteCommandService;
  private final FavoriteQueryService favoriteQueryService;

  @PostMapping("/favorites")
  public HankkiResponse<Void> createFavorite(@UserId final Long userId, @RequestBody @Valid final FavoritePostRequest request) {
    favoriteCommandService.createFavorite(FavoritePostCommand.of(userId, request));
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
  public HankkiResponse<FavoritesWithStatusGetResponse> getFavoritesWithStatus(@UserId Long id, @RequestParam("candidate") final Long storeId) {
    return HankkiResponse.success(CommonSuccessCode.OK, favoriteQueryService.findFavoritesWithStatus(FavoritesWithStatusGetCommand.of(id, storeId)));
  }

  @PostMapping("/favorites/{favoriteId}/shared")
  public HankkiResponse<Void> createSharedFavorite(@UserId final Long userId, @PathVariable(name = "favoriteId") long favoriteId, @RequestBody @Valid final FavoriteSharedPostRequest request) {
    favoriteCommandService.createSharedFavorite(FavoriteSharedPostCommand.of(userId, favoriteId, request.title(), request.details()));
    return HankkiResponse.success(CommonSuccessCode.CREATED);
  }

  @GetMapping("/favorites/{favoriteId}/ownership")
  public HankkiResponse<FavoriteOwnershipGetResponse> checkFavoriteOwnership(@UserId Long userId, @PathVariable("favoriteId") final long favoriteId) {
    return HankkiResponse.success(CommonSuccessCode.OK, favoriteQueryService.checkFavoriteOwnership(FavoriteOwnershipGetCommand.of(userId, favoriteId)));
  }

  @GetMapping("/favorites/{favoriteId}/users/me")
  public HankkiResponse<FavoriteUserNicknameGetResponse> getFavoriteUserNickname(@PathVariable("favoriteId") final long id) {
    return HankkiResponse.success(CommonSuccessCode.OK, favoriteQueryService.getFavoriteUserNickname(id));
  }
}
