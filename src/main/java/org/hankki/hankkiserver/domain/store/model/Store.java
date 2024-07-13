package org.hankki.hankkiserver.domain.store.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;
import org.hankki.hankkiserver.domain.common.Point;
import org.hankki.hankkiserver.domain.heart.model.Heart;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "store")
    private List<Heart> hearts;

    @OneToMany(mappedBy = "store")
    private List<StoreImage> images;

    @Embedded
    private Point point;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreCategory category;

    @Column(nullable = false)
    private int heartCount;

    @Column(nullable = false)
    private int lowestPrice;

    @Column(nullable = false)
    private boolean isDeleted;

    public void decreaseHeartCount() {
        this.heartCount--;
    }

    public void increaseHeartCount() {
        this.heartCount++;
    }
}
