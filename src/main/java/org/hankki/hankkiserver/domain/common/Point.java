package org.hankki.hankkiserver.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Point {

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

}
