package org.hankki.hankkiserver.domain.university.repository;

import org.hankki.hankkiserver.domain.university.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
