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
    private String platformId;
    @Enumerated(EnumType.STRING)
    private Platform platform;

    public static Member createUser(String name, String email, String platformId, Platform platform) {
        return Member.builder()
                .name(name)
                .email(email)
                .platformId(platformId)
                .platform(platform)
                .build();
    }
}
