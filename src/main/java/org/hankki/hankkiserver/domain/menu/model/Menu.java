package org.hankki.hankkiserver.domain.menu.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.api.store.controller.request.MenuPostRequest;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;
import org.hankki.hankkiserver.domain.store.model.Store;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Builder
    private Menu(Store store, String name, int price) {
        this.store = store;
        this.name = name;
        this.price = price;
    }

    public static Menu create(MenuPostRequest menuPostRequest, Store store) {
        return Menu.builder()
                .store(store)
                .name(menuPostRequest.name())
                .price(menuPostRequest.price())
                .build();
    }
}
