package org.hankki.hankkiserver.api.store.service.command;

import org.hankki.hankkiserver.api.store.controller.request.MenuPostRequest;
import org.hankki.hankkiserver.api.store.controller.request.StorePostRequest;
import org.hankki.hankkiserver.domain.store.model.StoreCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record StorePostCommand (
        MultipartFile image,
        String name,
        String address,
        StoreCategory category,
        double latitude,
        double longitude,
        Long universityId,
        Long userId,
        List<MenuPostRequest> menus
) {
    public static StorePostCommand of(final MultipartFile image, final StorePostRequest request, final Long userId) {
        return new StorePostCommand(image,
                request.name(),
                request.address(),
                request.category(),
                request.latitude(),
                request.longitude(),
                request.universityId(),
                userId,
                request.menus());
    }

    public Store toEntity() {
        return Store.builder()
                .name(name())
                .point(new Point(latitude(), longitude()))
                .address(address())
                .category(category())
                .lowestPrice(menus().stream().mapToInt(menu -> menu.price()).min().orElse(0))
                .heartCount(0)
                .isDeleted(false)
                .build();
    }
}
