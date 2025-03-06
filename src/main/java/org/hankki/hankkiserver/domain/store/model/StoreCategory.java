package org.hankki.hankkiserver.domain.store.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreCategory {

    ALL("전체"),
    KOREAN("한식"),
    JAPANESE("일식"),
    CHINESE("중식"),
    WESTERN("양식"),
    FASTFOOD("패스트푸드"),
    BUNSIK("분식"),
    CONVENIENCEFOOD("간편식"),
    SALADSANDWICH("샐러드"),
    WORLD("세계음식");

    private final String name;
    private String url;

    protected void setUrl(String url) {
        this.url = url;
    }
}