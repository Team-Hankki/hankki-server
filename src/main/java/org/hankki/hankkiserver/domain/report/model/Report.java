package org.hankki.hankkiserver.domain.report.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.common.BaseCreatedAtEntity;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.university.model.University;
import org.hankki.hankkiserver.domain.user.model.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseCreatedAtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @Builder
    private Report (User user, Store store, University university) {
        this.user = user;
        this.store = store;
        this.university = university;
    }

    public static Report create(final User user, final Store store, final University university) {
        return Report.builder()
                .user(user)
                .store(store)
                .university(university)
                .build();
    }
}
