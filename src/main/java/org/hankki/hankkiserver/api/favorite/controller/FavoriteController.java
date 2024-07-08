package org.hankki.hankkiserver.api.favorite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.favorite.service.FavoriteCommandService;
import org.hankki.hankkiserver.api.favorite.service.command.FavoritePostCommand;
import org.hankki.hankkiserver.api.favorite.controller.request.FavoritePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<?> createFavorite(@RequestBody @Valid final FavoritePostRequest favoritePostRequest) {

     return ResponseEntity.status(HttpStatus.CREATED)
         .header("Location", favoriteCommandService.create(FavoritePostCommand.of(1L, favoritePostRequest)).toString()
    ).build();

  }

  @DeleteMapping("/{favoriteId}")
  public ResponseEntity<?> deleteFavorite(@PathVariable(name = "favoriteId") final long favoriteId) {

    favoriteCommandService.delete(favoriteId);
    return ResponseEntity.noContent().build();

  }
}