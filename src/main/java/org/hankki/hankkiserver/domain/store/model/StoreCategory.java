package org.hankki.hankkiserver.domain.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreCategory {

    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    CONVENIENCEFOOD("간편식"),
    BUNSIK("분식"),
    SALAD("샐러드"),
    FASTFOOD("패스트푸드"),
    ETC("기타");

    private final String name;

}
