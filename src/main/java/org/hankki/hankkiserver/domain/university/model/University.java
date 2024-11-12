package org.hankki.hankkiserver.domain.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.Point;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @Embedded
    private Point point;

    @Column(nullable = false)
    private String name;

    public static University create(String name, Point point) {
        return University.builder()
            .name(name)
            .point(point)
            .build();
    }

    @Builder
    private University(String name, Point point) {
        this.name = name;
        this.point = point;
    }
}
