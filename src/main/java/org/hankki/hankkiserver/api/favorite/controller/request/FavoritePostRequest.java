package org.hankki.hankkiserver.api.favorite.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record FavoritePostRequest(
    @NotBlank(message = "제목이 비었습니다.")
    @Size(max = 18, message = "제목 길이가 18자를 초과했습니다.")
    String title,
    @Size(min = 1, max = 2, message = "해시태그 리스트 size가 1 이상 2 이하가 아닙니다.")
    List<@Size(min = 2, max= 10, message = "해시태그가 # 포함 10자를 초과했습니다.") String> details
) {
}
