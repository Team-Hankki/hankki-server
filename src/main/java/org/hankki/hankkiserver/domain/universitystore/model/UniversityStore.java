package org.hankki.hankkiserver.domain.universitystore.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hankki.hankkiserver.domain.store.model.Store;
import org.hankki.hankkiserver.domain.university.model.University;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniversityStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

}
