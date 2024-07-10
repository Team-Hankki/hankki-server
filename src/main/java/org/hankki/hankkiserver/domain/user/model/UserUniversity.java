package org.hankki.hankkiserver.domain.user.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "UserUniversity", timeToLive = 60 * 60 * 24 * 1000L * 30)
@Builder
@AllArgsConstructor
@Getter
public class UserUniversity {

    @Id
    private Long id;

    @Indexed
    private Long userId;

    private Long universityId;

    private String universityName;

    private Double longitude;

    private Double latitude;

}