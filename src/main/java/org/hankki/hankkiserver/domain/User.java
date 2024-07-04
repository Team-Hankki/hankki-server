package org.hankki.hankkiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
                .build();
    }

    public void softDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
        if (isDeleted) {
            this.deletedAt = LocalDateTime.now();
        }
    }
}
