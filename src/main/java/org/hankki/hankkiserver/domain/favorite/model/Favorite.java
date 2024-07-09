package org.hankki.hankkiserver.domain.favorite.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;
import org.hankki.hankkiserver.domain.favoritestore.model.FavoriteStore;
import org.hankki.hankkiserver.domain.user.model.User;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    private String detail;

    @Column(nullable = false)
    private String image_url;

    @OneToMany(mappedBy = "favorite")
    @BatchSize(size = 100)
    private List<FavoriteStore> favoriteStores = new ArrayList<>();

    public static Favorite create(User user, String name, String detail) {
        return Favorite.builder()
            .user(user)
            .name(name)
            .detail(detail)
            .build();
    }

    @Builder
    private Favorite(User user, String name, String detail) {
        this.user = user;
        this.name = name;
        this.detail = detail;
        this.image_url = "default.com";
    }
}
