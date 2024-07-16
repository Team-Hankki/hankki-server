package org.hankki.hankkiserver.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ImageInjector {
    public static final Map<Integer, String> imageUrls = new HashMap<>();

    @Value("${profile.image.one}")
    private String imageUrlOne;

    @Value("${profile.image.two}")
    private String imageUrlTwo;

    @Value("${profile.image.three}")
    private String imageUrlThree;

    @Value("${profile.image.four}")
    private String imageUrlFour;

    @Value("${profile.image.five}")
    private String imageUrlFive;

    @Value("${profile.image.six}")
    private String imageUrlSix;

    @Value("${profile.image.seven}")
    private String imageUrlSeven;

    @Value("${profile.image.eight}")
    private String imageUrlEight;

    @Value("${profile.image.nine}")
    private String imageUrlNine;

    @PostConstruct
    public void init() {
        imageUrls.put(1, imageUrlOne);
        imageUrls.put(2, imageUrlTwo);
        imageUrls.put(3, imageUrlThree);
        imageUrls.put(4, imageUrlFour);
        imageUrls.put(5, imageUrlFive);
        imageUrls.put(6, imageUrlSix);
        imageUrls.put(7, imageUrlSeven);
        imageUrls.put(8, imageUrlEight);
        imageUrls.put(9, imageUrlNine);
    }
}
