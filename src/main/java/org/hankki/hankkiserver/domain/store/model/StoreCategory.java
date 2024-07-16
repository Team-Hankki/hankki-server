package org.hankki.hankkiserver.domain.store.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreCategory {

    KOREAN("한식", "www."),
    CHINESE("중식", "www."),
    JAPANESE("일식", "www."),
    WESTERN("양식", "www."),
    CONVENIENCEFOOD("간편식", "www."),
    BUNSIK("분식", "www."),
    SALADSANDWICH("샐러드", "www."),
    FASTFOOD("패스트푸드", "www."),
    WORLD("세계음식", "www.");

    private final String name;
    private final String url;

}
