package org.hankki.hankkiserver.api.favorite.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record FavoritePostRequest(
    @NotBlank(message = "제목이 비었습니다.")
    @Size(max = 20, message = "제목 size가 20 이상입니다.")
    String title,
    List<String> details
) {
}
