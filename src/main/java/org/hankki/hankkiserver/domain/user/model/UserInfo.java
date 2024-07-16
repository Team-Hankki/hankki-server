package org.hankki.hankkiserver.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

import static org.hankki.hankkiserver.domain.ImageInjector.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    private String profileImageUrl;

    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static UserInfo createMemberInfo(final User user, final String refreshToken) {
        return UserInfo.builder()
                .user(user)
                .refreshToken(refreshToken)
                .profileImageUrl(setRandomDefaultImageUrl())
                .nickname(user.getName())
                .build();
    }

    public void updateRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateNickname(final String nickname) {
        this.nickname = nickname;
    }

    private static String setRandomDefaultImageUrl() {
        return imageUrls.get(new Random().nextInt(9) + 1);
    }
}
