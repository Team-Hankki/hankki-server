package org.hankki.hankkiserver.domain.menu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseCreatedAtEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "deleted_menu")
public class DeletedMenu extends BaseCreatedAtEntity {

    @Id
    @Column(name = "deleted_menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Builder
    private DeletedMenu(final String name, final int price, final Long storeId) {
        this.name = name;
        this.price = price;
        this.storeId = storeId;
    }

    public static DeletedMenu create(final String name, final int price, final Long storeId) {
        return DeletedMenu.builder()
                .name(name)
                .price(price)
                .storeId(storeId)
                .build();
    }
}
