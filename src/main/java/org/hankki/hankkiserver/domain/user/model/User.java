package org.hankki.hankkiserver.domain.user.model;

import jakarta.persistence.*;

import lombok.*;
import org.hankki.hankkiserver.domain.common.BaseTimeEntity;

import java.time.LocalDateTime;

import static org.hankki.hankkiserver.domain.user.model.MemberStatus.ACTIVE;
import static org.hankki.hankkiserver.domain.user.model.MemberStatus.INACTIVE;
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
    private Platform platform;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    public static User createUser(final String name, final String email, final String serialId, final Platform platform) {
        return User.builder()
                .name(name)
                .email(email)
                .serialId(serialId)
                .platform(platform)
                .userRole(USER)
                .memberStatus(ACTIVE)
                .build();
    }

    public void softDelete() {
        updateStatus(INACTIVE);
        this.deletedAt = LocalDateTime.now();
    }

    public void updateStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
}
