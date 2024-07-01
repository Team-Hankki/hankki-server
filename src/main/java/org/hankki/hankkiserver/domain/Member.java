package org.hankki.hankkiserver.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String serialId;
    @Enumerated(EnumType.STRING)
    private Platform platform;

    public static Member createUser(String name, String email, String serialId, Platform platform) {
        return Member.builder()
                .name(name)
                .email(email)
                .serialId(serialId)
                .platform(platform)
                .build();
    }
}
