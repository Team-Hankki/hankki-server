package org.hankki.hankkiserver.api.menu.controller;

import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.dto.HankkiResponse;
import org.hankki.hankkiserver.api.menu.service.MenuCommandService;
import org.hankki.hankkiserver.common.code.CommonSuccessCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
