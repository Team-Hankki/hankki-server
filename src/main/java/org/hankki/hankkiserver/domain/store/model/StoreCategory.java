package org.hankki.hankkiserver.domain.store.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreCategory {

    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    CONVENIENCEFOOD("간편식"),
    BUNSIK("분식"),
    SALADSANDWICH("샐러드"),
    FASTFOOD("패스트푸드"),
    WORLD("세계음식");

    private final String name;
    private String url;

    protected void setUrl(String url) {
        this.url = url;
    }
}