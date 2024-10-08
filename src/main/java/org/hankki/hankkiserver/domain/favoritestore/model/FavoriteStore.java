package org.hankki.hankkiserver.domain.favoritestore.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.favorite.model.Favorite;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@BatchSize(size = 100)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_id")
    private Favorite favorite;

    public static FavoriteStore create(Store store, Favorite favorite) {
        return FavoriteStore.builder()
            .store(store)
            .favorite(favorite)
            .build();
    }

    @Builder
    private FavoriteStore(Store store, Favorite favorite) {
        this.store = store;
        this.favorite = favorite;
    }

}
