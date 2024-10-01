package org.hankki.hankkiserver.api.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.menu.service.MenuCommandService;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenuPostResponse;
import org.hankki.hankkiserver.api.store.controller.request.MenuPostRequest;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuCommandService menuCommandService;

    @DeleteMapping("/menus/{id}")
    public HankkiResponse<Void> deleteMenu(@PathVariable final Long id) {
        menuCommandService.deleteMenu(id);
        return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
    }

    @PatchMapping("/menus/{id}")
    public HankkiResponse<Void> updateMenu(@PathVariable final Long id, @Valid @RequestBody final MenuPostRequest request) {
        menuCommandService.modifyMenu(MenuPatchCommand.of(id, request.name(), request.price()));
        return HankkiResponse.success(CommonSuccessCode.OK);
    }

    @PostMapping("/{storeId}/menus")
    public HankkiResponse<MenuPostResponse> createMenu(@PathVariable final Long storeId, @Valid @RequestBody final MenuPostRequest request) {
        return HankkiResponse.success(CommonSuccessCode.CREATED, menuCommandService.createMenu(MenuPostCommand.of(storeId, request.name(), request.price())));
    }
}
