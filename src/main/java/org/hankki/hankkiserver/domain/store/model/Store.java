package org.hankki.hankkiserver.domain.store.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;
import org.hankki.hankkiserver.domain.common.Point;
import org.hankki.hankkiserver.domain.heart.model.Heart;
import org.hankki.hankkiserver.domain.universitystore.model.UniversityStore;
import org.hibernate.annotations.BatchSize;

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

    @OneToMany(mappedBy = "store")
    @BatchSize(size = 100)
    private List<StoreImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<UniversityStore> universityStores = new ArrayList<>();

    public void decreaseHeartCount() {
        this.heartCount--;
    }

    public void increaseHeartCount() {
        this.heartCount++;
    }
}
