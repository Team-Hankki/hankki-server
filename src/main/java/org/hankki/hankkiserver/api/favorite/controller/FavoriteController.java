package org.hankki.hankkiserver.api.favorite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.BaseResponse;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoriteDeleteRequest;
import org.hankki.hankkiserver.api.favorite.service.FavoriteCommandService;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;
import org.hankki.hankkiserver.auth.UserId;
import org.hankki.hankkiserver.common.code.SuccessCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

  private final FavoriteCommandService favoriteCommandService;

  @PostMapping
  public ResponseEntity<?> createFavorite(@UserId final Long userId, @RequestBody @Valid final FavoritePostRequest favoritePostRequest) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .header("Location", favoriteCommandService.create(FavoritePostCommand.of(userId, favoritePostRequest)).toString()).build();

  }

  @DeleteMapping
  public ResponseEntity<?> deleteFavorite(@RequestBody @Valid final FavoriteDeleteRequest favoriteDeleteRequest) {

    favoriteCommandService.delete(favoriteDeleteRequest);
    return ResponseEntity.noContent().build();

  }
}