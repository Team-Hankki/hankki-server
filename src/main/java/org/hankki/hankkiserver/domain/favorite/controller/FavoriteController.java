package org.hankki.hankkiserver.domain.favorite.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.domain.favorite.service.FavoriteCommandService;
import org.hankki.hankkiserver.domain.favorite.service.dto.command.FavoritePostCommandDto;
import org.hankki.hankkiserver.domain.favorite.service.dto.request.FavoritePostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity createTask(@RequestBody FavoritePostDto favoritePostDto) {

    return ResponseEntity.created(
        URI.create(favoriteCommandService.create(FavoritePostCommandDto.of(1L, favoritePostDto)))
    ).body(HttpStatus.CREATED);
  }
}