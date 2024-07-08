package org.hankki.hankkiserver.api.controller.favorite;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.service.favorite.FavoriteCommandService;
import org.hankki.hankkiserver.api.service.favorite.command.FavoritePostCommand;
import org.hankki.hankkiserver.api.controller.favorite.request.FavoritePostRequest;
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
  public ResponseEntity<?> createFavorite(@RequestBody @Valid FavoritePostRequest favoritePostRequest) {

     return ResponseEntity.status(HttpStatus.CREATED)
        .header("Location", favoriteCommandService.create(FavoritePostCommand.of(1L, favoritePostRequest)).toString()
    ).build();

  }
}