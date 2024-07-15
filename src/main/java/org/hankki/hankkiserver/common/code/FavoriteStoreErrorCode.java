package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FavoriteStoreErrorCode implements ErrorCode {

  FAVORITE_STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 데이터입니다."),
  FAVORITE_STORE_ALREADY_EXISTED(HttpStatus.CONFLICT, "이미 족보에 추가된 가게입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
