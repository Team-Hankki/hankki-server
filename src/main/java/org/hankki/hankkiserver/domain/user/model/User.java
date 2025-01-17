package org.hankki.hankkiserver.domain.user.model;

import static org.hankki.hankkiserver.domain.user.model.UserRole.USER;
import static org.hankki.hankkiserver.domain.user.model.UserStatus.ACTIVE;
import static org.hankki.hankkiserver.domain.user.model.UserStatus.INACTIVE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;

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

    private static final String DELETED_USER_DEFAULT_INFO = "알 수 없음";

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
        this.name = DELETED_USER_DEFAULT_INFO;
        this.email = DELETED_USER_DEFAULT_INFO;
    }

    public void rejoin(final String name, final String email) {
        updateStatus(ACTIVE);
        updateDeletedAt(null);
        this.name = name;
        this.email = email;
    }

    private void updateStatus(final UserStatus userStatus) {
        this.status = userStatus;
    }

    private void updateDeletedAt(final LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
