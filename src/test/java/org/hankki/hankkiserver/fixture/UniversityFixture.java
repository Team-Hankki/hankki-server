package org.hankki.hankkiserver.fixture;

import org.hankki.hankkiserver.domain.common.Point;
import org.hankki.hankkiserver.domain.university.model.University;

public abstract class UniversityFixture {
    private UniversityFixture() {
    }

    public static University create() {
        return University.builder()
                .name("university")
                .point(new Point(1.0, 1.0))
                .build();
    }
}
