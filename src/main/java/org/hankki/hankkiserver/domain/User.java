package org.hankki.hankkiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

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

    public void delete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
