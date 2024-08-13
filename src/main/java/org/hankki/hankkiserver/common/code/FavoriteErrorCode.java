package org.hankki.hankkiserver.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FavoriteErrorCode implements ErrorCode {

  FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 족보입니다."),
  FAVORITE_TITLE_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 족보 제목입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
