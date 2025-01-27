package org.hankki.hankkiserver.api.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hankki.hankkiserver.api.common.PriceCategoryConverter;
import org.hankki.hankkiserver.api.common.UserIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserIdArgumentResolver userIdArgumentResolver;
    private final PriceCategoryConverter priceCategoryConverter;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userIdArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(priceCategoryConverter);
    }
}
