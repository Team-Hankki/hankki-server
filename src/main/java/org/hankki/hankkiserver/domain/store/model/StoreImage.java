package org.hankki.hankkiserver.domain.store.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseCreatedAtEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImage extends BaseCreatedAtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public StoreImage (Store store, String imageUrl) {
        this.store = store;
        this.imageUrl = imageUrl;
    }

    public static StoreImage createImage(Store store, String imageUrl) {
        return StoreImage.builder()
                .store(store)
                .imageUrl(imageUrl)
                .build();
    }
}
