package org.hankki.hankkiserver.domain.user.model;

import jakarta.persistence.*;

import lombok.*;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;
import org.hankki.hankkiserver.external.openfeign.dto.SocialInfoDto;

import java.time.LocalDateTime;

import static org.hankki.hankkiserver.domain.user.model.UserStatus.ACTIVE;
import static org.hankki.hankkiserver.domain.user.model.UserStatus.INACTIVE;
import static org.hankki.hankkiserver.domain.user.model.UserRole.USER;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String serialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    public static User createUser(final String name, final String email, final String serialId, final Platform platform) {
        return User.builder()
                .name(name)
                .email(email)
                .serialId(serialId)
                .platform(platform)
                .role(USER)
                .status(ACTIVE)
                .build();
    }

    public void softDelete() {
        updateStatus(INACTIVE);
        updateDeletedAt(LocalDateTime.now());
        this.name = "알 수 없음";
        this.email = "알 수 없음";
    }

    public void rejoin(final SocialInfoDto socialInfo) {
        updateStatus(ACTIVE);
        updateDeletedAt(null);
        this.name = socialInfo.name();
        this.email = socialInfo.email();
    }

    private void updateStatus(final UserStatus userStatus) {
        this.status = userStatus;
    }

    private void updateDeletedAt(final LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
