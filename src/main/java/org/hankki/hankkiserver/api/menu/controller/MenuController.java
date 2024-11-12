package org.hankki.hankkiserver.api.menu.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.menu.service.MenuCommandService;
import org.hankki.hankkiserver.api.menu.service.command.MenuDeleteCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPatchCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenuPostCommand;
import org.hankki.hankkiserver.api.menu.service.command.MenusPostCommand;
import org.hankki.hankkiserver.api.menu.service.response.MenusGetResponse;
import org.hankki.hankkiserver.api.menu.service.response.MenusPostResponse;
import org.hankki.hankkiserver.api.store.controller.request.MenuPostRequest;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class MenuController {

    private final MenuCommandService menuCommandService;

    @DeleteMapping("/{storeId}/menus/{id}")
    public HankkiResponse<Void> deleteMenu(@PathVariable("storeId") @Min(value = 1L) final long storeId,
                                           @PathVariable("id") @Min(value = 1L) final long id) {
        menuCommandService.deleteMenu(MenuDeleteCommand.of(storeId, id));
        return HankkiResponse.success(CommonSuccessCode.NO_CONTENT);
    }

    @PatchMapping("/{storeId}/menus/{id}")
    public HankkiResponse<Void> updateMenu(@PathVariable("storeId") @Min(value = 1L) final long storeId,
                                           @PathVariable("id") @Min(value = 1L) final long id,
                                           @Valid @RequestBody final MenuPostRequest request) {
        menuCommandService.modifyMenu(MenuPatchCommand.of(storeId, id, request.name(), request.price()));
        return HankkiResponse.success(CommonSuccessCode.OK);
    }

    @PostMapping("{storeId}/menus/bulk")
    public HankkiResponse<MenusPostResponse> createMenus(@PathVariable @Min(value = 1L) final long storeId,
                                                         @Valid @RequestBody final List<MenuPostRequest> request) {
        List<MenuPostCommand> command = request.stream()
                .map(r -> MenuPostCommand.of(r.name(), r.price()))
                .toList();
        return HankkiResponse.success(CommonSuccessCode.CREATED,
                menuCommandService.createMenus(MenusPostCommand.of(storeId, command)));
    }

    @GetMapping("{storeId}/menus")
    public HankkiResponse<MenusGetResponse> getMenus(@PathVariable final long storeId) {
        return HankkiResponse.success(CommonSuccessCode.OK, menuCommandService.getMenus(storeId));
    }
}
