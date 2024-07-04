package org.hankki.hankkiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static org.hankki.hankkiserver.domain.Role.PARTICIPANTS;

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
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String serialId;
    private boolean isDeleted;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    private LocalDateTime deletedAt;
    @Enumerated(EnumType.STRING)
    private Role role;

    public static User createUser(
            final String name,
            final String email,
            final String serialId,
            final Platform platform) {
        return User.builder()
                .name(name)
                .email(email)
                .serialId(serialId)
                .isDeleted(false)
                .platform(platform)
                .role(PARTICIPANTS)
                .build();
    }

    public void softDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
        if (isDeleted) {
            this.deletedAt = LocalDateTime.now();
        }
    }
}
