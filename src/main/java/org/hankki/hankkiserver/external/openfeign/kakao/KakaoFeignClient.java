package org.hankki.hankkiserver.external.openfeign.kakao;

import org.hankki.hankkiserver.external.openfeign.kakao.dto.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${oauth.kakao.name}", url = "${oauth.kakao.url}")
public interface KakaoFeignClient {

    @GetMapping
    KakaoUserInfo getUserInfo(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    );
}
