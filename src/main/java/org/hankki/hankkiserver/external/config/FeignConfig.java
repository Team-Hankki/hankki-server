package org.hankki.hankkiserver.external.config;

import org.hankki.hankkiserver.HankkiserverApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = HankkiserverApplication.class)
public class FeignConfig {
}
