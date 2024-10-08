package org.hankki.hankkiserver.api.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.menu.service.MenuCommandService;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPostCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenusPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenuListPostResponse;
import org.hankki.hankkiserver.api.store.controller.request.MenuPostRequest;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuCommandService menuCommandService;

    @DeleteMapping("/{storeId}/menus/{id}")
    public HankkiResponse<Void> deleteMenu(@PathVariable("storeId") final Long storeId, @PathVariable("id") final Long id) {
        menuCommandService.deleteMenu(MenuDeleteCommand.of(storeId, id));
        return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
    }

    @PatchMapping("/{storeId}/menus/{id}")
    public HankkiResponse<Void> updateMenu(@PathVariable("storeId") final Long storeId, @PathVariable("id") final Long id,
                                           @Valid @RequestBody final MenuPostRequest request) {
        menuCommandService.modifyMenu(MenuPatchCommand.of(storeId, id, request.name(), request.price()));
        return HankkiResponse.success(CommonSuccessCode.OK);
    }

    @PostMapping("/{storeId}/menus")
    public HankkiResponse<MenuListPostResponse> createMenu(@PathVariable final Long storeId, @Valid @RequestBody final List<MenuPostRequest> requests) {
        List<MenuPostCommand> commands = requests.stream()
                .map(request -> MenuPostCommand.of(request.name(), request.price()))
                .toList();
        return HankkiResponse.success(CommonSuccessCode.CREATED, menuCommandService.createMenus(MenusPostCommand.of(storeId, commands)));
    }
}
