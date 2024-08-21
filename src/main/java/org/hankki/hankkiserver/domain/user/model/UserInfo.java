package org.hankki.hankkiserver.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

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

    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static UserInfo createMemberInfo(final User user, final String refreshToken) {
        return UserInfo.builder()
                .user(user)
                .refreshToken(refreshToken)
                .nickname(user.getName())
                .build();
    }

    public void updateRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateNickname(final String nickname) {
        this.nickname = nickname;
    }

    public void softDelete() {
        updateRefreshToken(null);
        updateNickname("알 수 없음");
    }
}
