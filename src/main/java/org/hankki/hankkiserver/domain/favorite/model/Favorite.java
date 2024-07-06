package org.hankki.hankkiserver.domain.favorite.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;
import org.hankki.hankkiserver.domain.user.model.User;

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
    }
}
