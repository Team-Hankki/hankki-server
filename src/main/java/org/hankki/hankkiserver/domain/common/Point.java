package org.hankki.hankkiserver.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Point {

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

}
