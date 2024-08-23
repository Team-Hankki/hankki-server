package org.hankki.hankkiserver.domain.university.repository;

import org.hankki.hankkiserver.domain.university.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findAllByOrderByName();
}
