package org.hankki.hankkiserver.domain.store.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;
import org.hankki.hankkiserver.domain.common.Point;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@BatchSize(size = 100)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "store")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @BatchSize(size = 100)
    private List<StoreImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<UniversityStore> universityStores = new ArrayList<>();

    @Embedded
    private Point point;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreCategory category;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int heartCount;

    @Column(nullable = false)
    private int lowestPrice;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Store (String name, Point point, String address, StoreCategory category, int lowestPrice, int heartCount, boolean isDeleted) {
        this.name = name;
        this.point = point;
        this.address = address;
        this.category = category;
        this.lowestPrice = lowestPrice;
        this.heartCount = heartCount;
        this.isDeleted = isDeleted;
    }

    public void decreaseHeartCount() {
        this.heartCount--;
    }

    public void increaseHeartCount() {
        this.heartCount++;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public String getImageUrlOrElse() {
        return images.isEmpty() ? null : images.get(0).getImageUrl();
    }
}
